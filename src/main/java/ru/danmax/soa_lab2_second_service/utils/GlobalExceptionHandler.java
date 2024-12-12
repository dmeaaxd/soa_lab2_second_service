package ru.danmax.soa_lab2_second_service.utils;

import jakarta.persistence.EntityExistsException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.danmax.soa_lab2_second_service.Exceptions.IncorrectDataException;
import ru.danmax.soa_lab2_second_service.Exceptions.TeamSizeErrorException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityExistsException.class)
    public final ResponseEntity<?> EntityExistsException(EntityExistsException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", 409);
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IncorrectDataException.class)
    public final ResponseEntity<?> handleIncorrectDataException(IncorrectDataException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", 400);
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TeamSizeErrorException.class)
    public final ResponseEntity<?> handleTeamSizeErrorException(TeamSizeErrorException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", 409);
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public final ResponseEntity<?> handleObjectNotFoundException(ObjectNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", 404);
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}


