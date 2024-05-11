package raisetech.StudentManagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import raisetech.StudentManagement.exception.TestException;

@RestControllerAdvice
public class TestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TestException.class)
    public ResponseEntity<String> handleTestException(TestException ex) {
        System.out.println(ex.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
