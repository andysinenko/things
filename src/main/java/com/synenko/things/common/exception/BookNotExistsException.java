package com.synenko.things.common.exception;

public class BookNotExistsException extends BaseException {
    public BookNotExistsException(Long id) {
        super(String.format("Pdf book with id %s is not registered", id));
    }
}
