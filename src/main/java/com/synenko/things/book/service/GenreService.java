package com.synenko.things.book.service;

import com.synenko.things.book.controller.BookController;
import com.synenko.things.book.dto.AuthorMapper;
import com.synenko.things.book.dto.AuthorResponse;
import com.synenko.things.book.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.synenko.things.book.dto.GenreMapper;
import com.synenko.things.book.dto.GenreResponse;
import com.synenko.things.book.entity.Genre;
import com.synenko.things.book.repository.GenreRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class GenreService {
    private static final Logger logger = LoggerFactory.getLogger(GenreService.class);
    private GenreRepository genreRepository;
    private AuthorRepository authorRepository;

    public List<GenreResponse> getAllGenres() {
        return genreRepository.findAll()
                .stream()
                .map(GenreMapper::entityToDto)
                .toList();
    }

    public List<AuthorResponse> getAllAuthorsByGenre(Long genreId) {
        logger.info("getAllAuthorsByGenre {}", genreId);
        return AuthorMapper.entitiesToResponses(authorRepository.findByGenreId(genreId));
    }
}
