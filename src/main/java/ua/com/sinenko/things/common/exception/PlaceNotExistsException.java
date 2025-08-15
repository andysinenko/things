package ua.com.sinenko.things.common.exception;

public class PlaceNotExistsException extends BaseException {
    public PlaceNotExistsException(Long id) {
        super(String.format("Place with id %s is not registered", id));
    }
}
