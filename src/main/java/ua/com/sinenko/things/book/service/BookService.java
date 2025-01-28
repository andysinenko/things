package ua.com.sinenko.things.book.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.sinenko.things.book.entity.Book;
import ua.com.sinenko.things.book.entity.Genre;
import ua.com.sinenko.things.book.repository.BookRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class BookService {
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public void saveBook(Book book) {
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
        return bookRepository.findByGenre(genre);
    }

    public List<Book> getBooksByPublisher(String publisher) {
        return bookRepository.findByPublisher(publisher);
    }

    public List<Book> getBooksByYear(Date year) {
        return bookRepository.findByYear(year);
    }

    public Book getBooksById(Long id) {
        return bookRepository.findById(id).orElse(new Book());
    }
}
