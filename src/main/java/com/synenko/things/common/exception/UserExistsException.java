package com.synenko.things.common.exception;

public class UserExistsException extends BaseException {
    public UserExistsException(String name) {
        super(String.format("User %s is already registered", name));
    }

    public UserExistsException(Long id) {
        super(String.format("User %d is already registered", id));
    }
}
