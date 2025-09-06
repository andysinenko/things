package ua.com.sinenko.things.book.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.sinenko.things.book.dto.AuthorDto;
import ua.com.sinenko.things.book.dto.BookDto;
import ua.com.sinenko.things.book.dto.BookMapper;
import ua.com.sinenko.things.book.entity.Author;
import ua.com.sinenko.things.book.entity.Book;
import ua.com.sinenko.things.book.entity.Genre;
import ua.com.sinenko.things.book.repository.AuthorRepository;
import ua.com.sinenko.things.book.repository.BookRepository;
import ua.com.sinenko.things.book.repository.GenreRepository;
import ua.com.sinenko.things.book.repository.SeriesRepository;
import ua.com.sinenko.things.common.exception.PlaceNotExistsException;
import ua.com.sinenko.things.common.exception.aop.ThLogger;
import ua.com.sinenko.things.place.repository.PlaceRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final SeriesRepository seriesRepository;
    private final PlaceRepository placeRepository;

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
        /*var authors = authorRepository.findByIdIn(bookDto.authors().stream().map(e-> e.id()).collect(Collectors.toList()));
        var genre = genreRepository.findById(bookDto.genre().id());
        var series = seriesRepository.findById(bookDto.series().id());

        if (authors.isEmpty() || genre.isEmpty() || series.isEmpty()) {
            throw new IllegalArgumentException("Author, genre or series not found");
        }

        Book book = BookMapper.dtoToEntity(bookDto);
        book.setAuthors(authors);
        book.setGenre(genre.get());
        book.setSeries(series.get());
        book.setYear(LocalDate.parse(bookDto.year() + "-01-01"));

        for (Author author : authors) {
            author.getBooks().add(book);
        }*/
        var book = BookMapper.dtoToEntity(bookDto);
        logger.info("Book before updating: {}", book);

        return bookRepository.saveAndFlush(book);
    }

    public Book updateBook(Long id, BookDto bookDto) {
        /*var book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        var genre = genreRepository.findById(bookDto.genre().id()).orElse(null);
        var series = seriesRepository.findById(bookDto.series().id()).orElse(null);
        var place = placeRepository.findById(bookDto.place().id()).orElseThrow(() -> new PlaceNotExistsException(bookDto.place().id()));

        var authors = getAuthors(bookDto.authors());
        logger.info("!!! authors {}", authors);

        book.setAuthors(authors);
        for (Author author : authors) {
            author.getBooks().add(book);
        }

        book.setTitle(bookDto.title());
        book.setVolumeNumber(bookDto.volume());
        book.setGenre(genre);
        book.setSeries(series);
        book.setYear(bookDto.year());
        book.setPlace(place);
        book.setDescription(bookDto.description());*/
        var book = BookMapper.dtoToEntity(bookDto);
        logger.info("Book before updating: {}", book);

        return bookRepository.saveAndFlush(book);
    }

    private List<Author> getAuthors(List<AuthorDto> authorDtos) {
        logger.info("!!! authorDtos {}", authorDtos);
        if(authorDtos != null) {
            Collection<Long> authorIds = authorDtos.stream().map(e -> e.id()).collect(Collectors.toList());
            List<Author> authors = authorRepository.findByIdIn(authorIds);
            return authors;
        }
        return null;
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
