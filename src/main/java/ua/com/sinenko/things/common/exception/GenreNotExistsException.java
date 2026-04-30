package ua.com.sinenko.things.common.exception;

public class GenreNotExistsException extends BaseException {
    public GenreNotExistsException(Long id) {
        super(String.format("Genre with id %s is not registered", id));
    }
}
