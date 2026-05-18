package ua.com.sinenko.things.book.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.com.sinenko.things.book.dto.AuthorMapper;
import ua.com.sinenko.things.book.dto.AuthorResponse;
import ua.com.sinenko.things.book.entity.Author;
import ua.com.sinenko.things.book.repository.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Cacheable("authors")
    public List<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorMapper::entityToResponse)
                .toList();
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
