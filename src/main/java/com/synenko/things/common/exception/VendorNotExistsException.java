package com.synenko.things.common.exception;

public class VendorNotExistsException extends BaseException {
    public VendorNotExistsException(Long id) {
        super(String.format("Vendor with id %s is not registered", id));
    }
}
