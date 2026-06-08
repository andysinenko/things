package com.synenko.things.place.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.synenko.things.common.exception.PlaceNotExistsException;
import com.synenko.things.place.dto.PlaceMapper;
import com.synenko.things.place.dto.PlaceRequest;
import com.synenko.things.place.entity.Place;
import com.synenko.things.place.repository.PlaceRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class PlaceService {
    private PlaceRepository placeRepository;

    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    public List<Place> getPlacesByName(String name) {
        return placeRepository.findByName(name);
    }

    public Place getPlaceById(Long id) {
        return placeRepository.findById(id).orElseGet(null);
    }

    @Transactional
    public void savePlace(PlaceRequest placeRequest) {
        var parent = placeRepository.findById(placeRequest.parent());
        var place = PlaceMapper.requestToEntity(placeRequest, parent.orElse(null));
        placeRepository.save(place);
    }

    public void deletePlace(Long id) {
        placeRepository.deleteById(id);
    }

    @Transactional
    public void updatePlace(PlaceRequest placeRequest, Long id) {
        var place = placeRepository.findById(id)
                .orElseThrow(() -> new PlaceNotExistsException(id));

        var parent = placeRequest.parent() != null
                ? placeRepository.findById(placeRequest.parent())
                    .orElseThrow(() -> new PlaceNotExistsException(placeRequest.parent()))
                : null;

        place.setName(placeRequest.name());
        place.setDescription(placeRequest.description());
        place.setLevel(placeRequest.level());
        place.setParent(parent);

        placeRepository.save(place);
    }
}