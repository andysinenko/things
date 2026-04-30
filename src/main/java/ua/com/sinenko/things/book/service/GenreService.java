package ua.com.sinenko.things.book.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.sinenko.things.book.entity.Genre;
import ua.com.sinenko.things.book.repository.GenreRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class GenreService {
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreByName(String name) {
        return genreRepository.findGenreByName(name);
    }
}
