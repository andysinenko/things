package ua.com.sinenko.things.book.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.sinenko.things.book.dto.AuthorResponse;
import ua.com.sinenko.things.book.dto.BookRequest;
import ua.com.sinenko.things.book.dto.BookMapper;
import ua.com.sinenko.things.book.entity.Author;
import ua.com.sinenko.things.book.entity.Book;
import ua.com.sinenko.things.book.entity.Genre;
import ua.com.sinenko.things.book.entity.Series;
import ua.com.sinenko.things.book.repository.AuthorRepository;
import ua.com.sinenko.things.book.repository.BookRepository;
import ua.com.sinenko.things.book.repository.GenreRepository;
import ua.com.sinenko.things.book.repository.SeriesRepository;
import ua.com.sinenko.things.common.exception.GenreNotExistsException;
import ua.com.sinenko.things.common.exception.PlaceNotExistsException;
import ua.com.sinenko.things.common.exception.SeriesNotExistsException;
import ua.com.sinenko.things.place.entity.Place;
import ua.com.sinenko.things.place.repository.PlaceRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
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
    private final CacheManager cacheManager;


    @Transactional(readOnly = true)
    @Cacheable(value = "bookCount")
    public long getBookCount() {
        return bookRepository.count();
    }

    @Cacheable(value = "booksPage", key = "#pageNumber + '-' + #pageSize")
    @Transactional(readOnly = true)
    public Page<Book> getAllBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"));

        Page<Long> idsPage = bookRepository.findAllIds(pageable);
        if (idsPage.isEmpty()) {
            return Page.empty(pageable);
        }

        List<Book> books = bookRepository.findAllWithAssociationsByIds(idsPage.getContent());

        Map<Long, Book> bookMap = books.stream()
                .collect(Collectors.toMap(Book::getId, Function.identity()));
        List<Book> ordered = idsPage.getContent()
                .stream()
                .map(bookMap::get)
                .filter(Objects::nonNull)
                .toList();

        return new PageImpl<>(ordered, pageable, idsPage.getTotalElements());
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @CacheEvict(value = "bookCount", allEntries = true)
    public Book saveBook(BookRequest bookRequest) {
        var book = getFullfilledBookEntity(bookRequest);
        logger.debug("Book before updating: {}", book);

        return bookRepository.saveAndFlush(book);
    }

    @CacheEvict(value = "bookCount", allEntries = true)
    @Transactional
    public Book updateBook(Long id, BookRequest bookRequest) {
        var book = getFullfilledBookEntity(bookRequest);
        logger.info("Book before updating: {}", book);

        return bookRepository.saveAndFlush(book);
    }

    @Cacheable(value = "booksPage", key = "#pageNumber + '-' + #pageSize")
    private Book getFullfilledBookEntity(BookRequest bookRequest) {
        List<Author> authors = authorRepository.findAllById(bookRequest.authors());

        Genre genre = genreRepository.findById(bookRequest.genre())
                .orElseThrow(() -> new GenreNotExistsException(bookRequest.genre()));

        Place place = placeRepository.findById(bookRequest.place())
                .orElseThrow(() -> new PlaceNotExistsException(bookRequest.place()));

        Series series = seriesRepository.findById(bookRequest.series())
                .orElseThrow(() -> new SeriesNotExistsException(bookRequest.series()));

        return BookMapper.dtoToEntity(bookRequest, authors, genre, series, place);
    }

    private List<Author> getAuthors(List<AuthorResponse> authorResponses) {
        logger.info("!!! authorDtos {}", authorResponses);
        if (authorResponses != null) {
            List<String> authorIds = authorResponses.stream().map(e -> e.name()).collect(Collectors.toList());
            List<Author> authors = authorRepository.findByNameIn(authorIds);
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
