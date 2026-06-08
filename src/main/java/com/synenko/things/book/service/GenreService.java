package com.synenko.things.book.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.synenko.things.book.dto.GenreMapper;
import com.synenko.things.book.dto.GenreResponse;
import com.synenko.things.book.entity.Genre;
import com.synenko.things.book.repository.GenreRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class GenreService {
    private GenreRepository genreRepository;

    public List<GenreResponse> getAllGenres() {
        return genreRepository.findAll()
                .stream()
                .map(GenreMapper::entityToDto)
                .toList();
    }

    public Genre getGenreByName(String name) {
        return genreRepository.findGenreByName(name);
    }
}
