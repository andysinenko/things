package com.synenko.things.common.exception;

public class PdfBookNotExistsException extends BaseException {
    public PdfBookNotExistsException(Long id) {
        super(String.format("Pdf book with id %s is not registered", id));
    }
}
