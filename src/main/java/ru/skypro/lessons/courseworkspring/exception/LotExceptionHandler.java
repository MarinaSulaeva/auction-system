package ru.skypro.lessons.courseworkspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LotExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleLotIdException(LotIdException lotIdException) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<?> handleLotWrongStatusException(LotWrongStatusException lotWrongStatusException) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleLotInvalidInformationException(InvalidInformationException invalidInformationException) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
