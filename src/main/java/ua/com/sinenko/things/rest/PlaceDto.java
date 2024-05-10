package ua.com.sinenko.things.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PlaceDto {
    private String placeId;
    private String name;
    private String description;
}
