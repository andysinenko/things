package ua.com.sinenko.things.book.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.sinenko.things.book.dto.GenreMapper;
import ua.com.sinenko.things.book.dto.GenreResponse;
import ua.com.sinenko.things.book.entity.Genre;
import ua.com.sinenko.things.book.repository.GenreRepository;

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
