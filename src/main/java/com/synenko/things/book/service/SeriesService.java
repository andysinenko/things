package com.synenko.things.book.service;

import com.synenko.things.book.dto.*;
import com.synenko.things.common.exception.SeriesNotExistsException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.synenko.things.book.repository.SeriesRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class SeriesService {
    private static final Logger logger = LoggerFactory.getLogger(SeriesService.class);

    private SeriesRepository seriesRepository;

    @Cacheable("series")
    public List<SeriesResponse> getAllSeries() {
        return seriesRepository.findAll()
                .stream()
                .map(SeriesMapper::entityToDto)
                .toList();
    }

    @Transactional
    @CacheEvict(value = "series", allEntries = true)
    public SeriesResponse updateSeries(SeriesRequest seriesRequest, Long seriesId) {
        var series = seriesRepository.findById(seriesId).orElseThrow(() -> new SeriesNotExistsException(seriesId));
        series.setName(seriesRequest.name());
        series.setNote(seriesRequest.note());

        var saved = seriesRepository.save(series);
        return SeriesMapper.entityToDto(saved);
    }

    @Transactional
    @CacheEvict(value = "series", allEntries = true)
    public void deleteSeries(Long seriesId) {
        seriesRepository.deleteById(seriesId);
    }

    @Transactional
    @CacheEvict(value = "series", allEntries = true)
    public SeriesResponse saveSeries(SeriesRequest seriesRequest) {
        var newSeries = SeriesMapper.maptoToEntity(seriesRequest);
        var saved = seriesRepository.save(newSeries);
        logger.debug("save series {}", saved);
        return SeriesMapper.entityToDto(saved);
    }
}
