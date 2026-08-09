package com.synenko.things.book.service;

import com.synenko.things.book.dto.AuthorRequest;
import com.synenko.things.book.repository.GenreRepository;
import com.synenko.things.common.exception.AuthorNotExistsException;
import com.synenko.things.common.exception.GenreNotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.synenko.things.book.dto.AuthorMapper;
import com.synenko.things.book.dto.AuthorResponse;
import com.synenko.things.book.entity.Author;
import com.synenko.things.book.repository.AuthorRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Cacheable("authors")
    public List<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorMapper::entityToResponse)
                .toList();
    }

    @Transactional
    @CacheEvict(value = "authors", allEntries = true)
    public AuthorResponse saveAuthor(AuthorRequest authorRequest) {
        var genre = genreRepository.findGenreById(authorRequest.genre_id())
                .orElseThrow(()-> new GenreNotExistsException(authorRequest.genre_id()));

        return AuthorMapper.entityToResponse(authorRepository.save(Author.builder()
                .name(authorRequest.name())
                .note(authorRequest.note())
                .genre(genre)
                .build()));
    }

    @Transactional
    @CacheEvict(value = "authors", allEntries = true)
    public Author updateAuthor(AuthorRequest authorRequest, Long authorId) {
        var author = authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotExistsException(authorId));
        var genre = genreRepository.findGenreById(authorRequest.genre_id()).orElseThrow(()-> new GenreNotExistsException(authorRequest.genre_id()));
        author.setGenre(genre);
        author.setName(authorRequest.name());
        author.setNote(authorRequest.note());

        return authorRepository.save(author);
    }

    @Transactional
    public void deleteAuthor(Long authorId) {
        authorRepository.deleteById(authorId);
    }
}
