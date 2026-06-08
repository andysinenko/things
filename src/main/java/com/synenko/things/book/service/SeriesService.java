package com.synenko.things.book.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.synenko.things.book.dto.SeriesMapper;
import com.synenko.things.book.dto.SeriesResponse;
import com.synenko.things.book.repository.SeriesRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class SeriesService {
    private SeriesRepository seriesRepository;

    public List<SeriesResponse> getAllSeries() {
        return seriesRepository.findAll()
                .stream()
                .map(SeriesMapper::entityToDto)
                .toList();
    }
}
