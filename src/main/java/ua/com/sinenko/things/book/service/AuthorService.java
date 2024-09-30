package ua.com.sinenko.things.book.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.sinenko.things.book.entity.Author;
import ua.com.sinenko.things.book.repository.AuthorRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class AuthorService {
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author getAuthorByName(String name) {
        return authorRepository.findByName(name);
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public void updateAuthor(Author author) {
        authorRepository.save(author);
    }
}
