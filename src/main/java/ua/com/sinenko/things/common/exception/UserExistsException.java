package ua.com.sinenko.things.common.exception;

public class UserExistsException extends BaseException {
    public UserExistsException(String name) {
        super(String.format("User %s is already registered", name));
    }
}
