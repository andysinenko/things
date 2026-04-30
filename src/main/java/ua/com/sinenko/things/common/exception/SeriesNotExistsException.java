package ua.com.sinenko.things.common.exception;

public class SeriesNotExistsException extends BaseException {
    public SeriesNotExistsException(Long id) {
        super(String.format("Series with id %s is not registered", id));
    }
}
