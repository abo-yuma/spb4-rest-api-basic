package com.aboyuma.booksrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<BookErrorResponse>
    handleBookNotFoundException(

        BookNotFoundException exc
    ) {

        BookErrorResponse error =
            new BookErrorResponse(

                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis()
            );

        return new ResponseEntity<>(
            error,
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BookErrorResponse>
    handleGenericException(

        Exception exc
    ) {

        BookErrorResponse error =
            new BookErrorResponse(

                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong",
                System.currentTimeMillis()
            );

        return new ResponseEntity<>(
            error,
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}