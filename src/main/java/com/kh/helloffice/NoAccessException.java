package com.kh.helloffice;

public class NoAccessException extends RuntimeException {

    public NoAccessException() {

    }
    public NoAccessException(String s) {
        super(s);
    }
}
