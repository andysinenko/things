package ua.com.sinenko.things.book.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.sinenko.things.book.dto.BookDto;
import ua.com.sinenko.things.book.dto.BookMapper;
import ua.com.sinenko.things.book.entity.Book;
import ua.com.sinenko.things.book.entity.Genre;
import ua.com.sinenko.things.book.repository.AuthorRepository;
import ua.com.sinenko.things.book.repository.BookRepository;
import ua.com.sinenko.things.book.repository.GenreRepository;
import ua.com.sinenko.things.book.repository.SeriesRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookService {
    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private AuthorRepository authorRepository;
    private SeriesRepository seriesRepository;

    public Page<Book> getAllBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findAll(pageable);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public Book saveBook(BookDto bookDto) {
        var authors = authorRepository.findByIdIn(bookDto.author().stream().map(e-> e.id()).collect(Collectors.toSet()));
        var genre = genreRepository.findById(bookDto.genre());
        var series = seriesRepository.findById(bookDto.series());

        if (authors.isEmpty() || genre.isEmpty() || series.isEmpty()) {
            throw new IllegalArgumentException("Author, genre or series not found");
        }

        Book book = BookMapper.mapDtoToEntity(bookDto);
        book.setAuthors(authors);
        book.setGenre(genre.get());
        book.setSeries(series.get());
        book.setYear(LocalDate.parse(bookDto.year() + "-01-01"));

        return bookRepository.save(book);
    }

    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> getBooksByAuthorName(String name) {
        return bookRepository.findByAuthorsName(name);
    }

    public List<Book> getBooksByGenre(Genre genre) {
        return bookRepository.findBooksByGenre(genre);
    }

    public List<Book> getBooksByYear(LocalDate year) {
        return bookRepository.findByYear(year);
    }

    public Book getBooksById(Long id) {
        return bookRepository.findById(id).orElse(new Book());
    }
}
