package ua.com.sinenko.things.book.service;

import lombok.AllArgsConstructor;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookService {
    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private AuthorRepository authorRepository;
    private SeriesRepository seriesRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void saveBook(BookDto bookDto) {
        var author = authorRepository.findById(bookDto.author());
        var genre = genreRepository.findById(bookDto.genre());
        var series = seriesRepository.findById(bookDto.series());

        if (author.isEmpty() || genre.isEmpty() || series.isEmpty()) {
            throw new IllegalArgumentException("Author, genre or series not found");
        }

        Book book = BookMapper.mapDtoToEntity(bookDto);
        book.setAuthors(Set.of(author.get()));
        book.setGenre(genre.get());
        book.setSeries(series.get());
        book.setYear(LocalDate.parse(bookDto.year() + "-01-01"));

        bookRepository.save(book);
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
