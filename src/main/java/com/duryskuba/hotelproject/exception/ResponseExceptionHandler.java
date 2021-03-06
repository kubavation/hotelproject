package com.duryskuba.hotelproject.exception;


import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import com.duryskuba.hotelproject.model.ErrorDetails;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getContextPath()),
               HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorDetails(LocalDateTime.now(),"Validation failed",ex.getBindingResult().toString()),
             HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<Object> handleAuthException (Exception ex, WebRequest req) {
        return new ResponseEntity<>(
                new ErrorDetails(LocalDateTime.now(), ex.getMessage(), req.getContextPath()),
                HttpStatus.UNAUTHORIZED);

    }


    @ExceptionHandler(ResourceAlreadyExistsException.class)
    protected ResponseEntity<Object> handleResourceAlreadyExistsException (Exception ex, WebRequest req) {
        return new ResponseEntity<>(
                new ErrorDetails(LocalDateTime.now(), ex.getMessage(), req.getContextPath()),
                HttpStatus.CONFLICT);

    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllEsception(Exception ex, WebRequest req) {
        return new ResponseEntity<>(
                new ErrorDetails(LocalDateTime.now(), ex.getMessage(), req.getContextPath()),
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
