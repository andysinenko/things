package ua.com.sinenko.things.place.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.sinenko.things.place.entity.Place;
import ua.com.sinenko.things.place.repository.PlaceRepository;

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
        return placeRepository.findById(id).get();
    }

    public void savePlace(Place place) {
        placeRepository.save(place);
    }

    public void deletePlace(Long id) {
        placeRepository.deleteById(id);
    }

    public void updatePlace(Place place) {
        placeRepository.save(place);
    }

}
