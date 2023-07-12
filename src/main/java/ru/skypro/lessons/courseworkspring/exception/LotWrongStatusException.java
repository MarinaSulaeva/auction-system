package ru.skypro.lessons.courseworkspring.exception;

public class LotWrongStatusException extends RuntimeException{
    public LotWrongStatusException() {
    }

    public LotWrongStatusException(String message) {
        super(message);
    }
}
