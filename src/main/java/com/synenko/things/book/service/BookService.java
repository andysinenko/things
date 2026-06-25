package com.synenko.things.book.service;

import com.synenko.things.book.dto.BookMapper;
import com.synenko.things.book.dto.BookPageResponse;
import com.synenko.things.book.dto.BookRequest;
import com.synenko.things.common.exception.BookNotExistsException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.synenko.things.book.dto.*;
import com.synenko.things.book.entity.Author;
import com.synenko.things.book.entity.Book;
import com.synenko.things.book.entity.Genre;
import com.synenko.things.book.entity.Series;
import com.synenko.things.book.repository.AuthorRepository;
import com.synenko.things.book.repository.BookRepository;
import com.synenko.things.book.repository.GenreRepository;
import com.synenko.things.book.repository.SeriesRepository;
import com.synenko.things.common.exception.GenreNotExistsException;
import com.synenko.things.common.exception.PlaceNotExistsException;
import com.synenko.things.common.exception.SeriesNotExistsException;
import com.synenko.things.place.entity.Place;
import com.synenko.things.place.repository.PlaceRepository;

import java.time.LocalDate;
import java.util.ArrayList;
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


    @Transactional(readOnly = true)
    @Cacheable(value = "bookCount")
    public long getBookCount() {
        return bookRepository.count();
    }

    @Cacheable(value = "booksPage", key = "#pageNumber + '-' + #pageSize")
    @Transactional(readOnly = true)
    public BookPageResponse getAllBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"));

        Page<Long> idsPage = bookRepository.findAllIds(pageable);
        if (idsPage.isEmpty()) {
            return new BookPageResponse(List.of(), 0, 0, 0);
        }

        List<Book> books = bookRepository.findAllWithAssociationsByIds(idsPage.getContent());

        Map<Long, Book> bookMap = books.stream()
                .collect(Collectors.toMap(Book::getId, Function.identity()));
        List<Book> ordered = idsPage.getContent()
                .stream()
                .map(bookMap::get)
                .filter(Objects::nonNull)
                .toList();

        var pagableResponse = new PageImpl<>(ordered, pageable, idsPage.getTotalElements());
        return BookMapper.entityToPagebleResponse(pagableResponse.map(BookMapper::entityToResponse));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "booksPage", key = "#pageNumber + '-' + #pageSize + '-' + #sort")
    public BookPageResponse getAllBooksOld(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return BookMapper.entityToPagebleResponse(bookRepository.findAll(pageable).map(BookMapper::entityToResponse));
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

    @CacheEvict(value = {"bookCount", "booksPage"}, allEntries = true)
    @Transactional
    public Book updateBook(Long id, BookRequest bookRequest) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotExistsException(id));

        List<Author> authors = authorRepository.findAllById(bookRequest.authors());

        Genre genre = genreRepository.findById(bookRequest.genre())
                .orElseThrow(() -> new GenreNotExistsException(bookRequest.genre()));

        Place place = placeRepository.findById(bookRequest.place())
                .orElseThrow(() -> new PlaceNotExistsException(bookRequest.place()));

        Series series = seriesRepository.findById(bookRequest.series())
                .orElseThrow(() -> new SeriesNotExistsException(bookRequest.series()));

        book.setTitle(bookRequest.title());
        book.setDescription(bookRequest.description());
        book.setYear(bookRequest.year());
        book.setVolumeNumber(bookRequest.volume());
        book.setAuthors(authors);
        book.setGenre(genre);
        book.setPlace(place);
        book.setSeries(series);

        logger.debug("Book before updating: {}", book);

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

    @Transactional(readOnly = true)
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthorName(String name) {
        return bookRepository.findByAuthorsName(name);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByGenre(Genre genre) {
        return bookRepository.findBooksByGenre(genre);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByYear(LocalDate year) {
        return bookRepository.findByYear(year);
    }

    @Transactional(readOnly = true)
    public Book getBooksById(Long id) {
        return bookRepository.findById(id).orElse(new Book());
    }
}
