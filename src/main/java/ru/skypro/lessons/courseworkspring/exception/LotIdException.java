package ru.skypro.lessons.courseworkspring.exception;

public class LotIdException extends IllegalArgumentException{
    public LotIdException() {
    }

    public LotIdException(String s) {
        super(s);
    }
}
