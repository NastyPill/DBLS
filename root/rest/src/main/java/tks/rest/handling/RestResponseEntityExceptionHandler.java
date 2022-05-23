package tks.rest.handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tks.model.exceptions.EntityNotFoundException;
import tks.model.exceptions.TooWideTimeIntervalException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { EntityNotFoundException.class })
    protected ResponseEntity<String> handleNotFound() {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = { TooWideTimeIntervalException.class })
    protected ResponseEntity<String> handleTooWideInterval() {
        return new ResponseEntity<>("Too wide time interval", HttpStatus.BAD_REQUEST);
    }
}