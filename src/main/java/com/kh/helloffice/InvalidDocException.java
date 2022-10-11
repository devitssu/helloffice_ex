package com.kh.helloffice;

public class InvalidDocException extends RuntimeException{
    public InvalidDocException() {

    }
    public InvalidDocException(String s) {
        super(s);
    }
}
