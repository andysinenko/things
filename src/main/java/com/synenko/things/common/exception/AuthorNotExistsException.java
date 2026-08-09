package com.synenko.things.common.exception;

public class AuthorNotExistsException extends BaseException {
    public AuthorNotExistsException(Long id) {
        super(String.format("Author with id %s is not registered", id));
    }
}
