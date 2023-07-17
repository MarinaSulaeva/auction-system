package ru.skypro.lessons.courseworkspring.exception;

public class InvalidInformationException extends IllegalArgumentException{

    public InvalidInformationException(String s) {
        super(s);
    }
}
