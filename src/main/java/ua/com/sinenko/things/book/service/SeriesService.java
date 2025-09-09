package ua.com.sinenko.things.book.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.sinenko.things.book.entity.Series;
import ua.com.sinenko.things.book.repository.SeriesRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class SeriesService {
    private SeriesRepository seriesRepository;

    public List<Series> getAllSeries() {
        return seriesRepository.findAll();
    }
}
