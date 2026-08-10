package com.synenko.things.book.service;

import com.synenko.things.book.dto.*;
import com.synenko.things.book.repository.AuthorRepository;
import com.synenko.things.common.exception.GenreNotExistsException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.synenko.things.book.repository.GenreRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class GenreService {
    private static final Logger logger = LoggerFactory.getLogger(GenreService.class);
    private GenreRepository genreRepository;
    private AuthorRepository authorRepository;

    @Cacheable("genres")
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

    @Transactional
    @CacheEvict(value = "genres", allEntries = true)
    public GenreResponse updateGenre(GenreRequest genreRequest, Long genreId) {
        var genre = genreRepository.findById(genreId).orElseThrow(() -> new GenreNotExistsException(genreId));
        genre.setName(genreRequest.name());
        genre.setNote(genreRequest.note());

        var saved = genreRepository.save(genre);
        return GenreMapper.entityToDto(saved);
    }

    @Transactional
    @CacheEvict(value = "genres", allEntries = true)
    public void deleteGenre(Long genreId) {
        genreRepository.deleteById(genreId);
    }

    @Transactional
    @CacheEvict(value = "genres", allEntries = true)
    public GenreResponse saveGenre(GenreRequest genreRequest) {
        var newGenre = GenreMapper.dtoToEntity(genreRequest);
        var saved = genreRepository.save(newGenre);
        logger.debug("save genre {}", saved);
        return GenreMapper.entityToDto(saved);
    }
}
