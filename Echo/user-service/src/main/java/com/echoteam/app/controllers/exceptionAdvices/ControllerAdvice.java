package com.echoteam.app.controllers.exceptionAdvices;

import com.echoteam.app.entities.User;
import com.echoteam.app.entities.UserAuth;
import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.entities.UserRole;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import com.echoteam.app.exceptions.SomethingWrongException;
import jakarta.persistence.Column;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.JoinColumn;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Field;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = SomethingWrongException.class)
    public ResponseEntity<ErrorResponse> handelSomethingWrongExtension(SomethingWrongException ex) {
        var errorResponse = ErrorResponse.builder(ex, HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage()).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = ParameterIsNotValidException.class)
    public ResponseEntity<ErrorResponse> handleParameterIsNotValidException(ParameterIsNotValidException ex) {
        var errorResponse = ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage()).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = recognizeDataIntegrityViolationException(ex);
        var errorResponse = ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, errorMessage);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        var errorResponse = ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // обробляє валідаційні винятки
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Error");
        problemDetail.setDetail("One or more fields have validation errors.");
        problemDetail.setInstance(ex.getBody().getInstance());
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> problemDetail.setProperty(error.getField(),error.getDefaultMessage()));

        var errorResponse = ErrorResponse.builder(ex, problemDetail).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    private Class<?> recognizeClassByException(DataIntegrityViolationException ex) {
        String message = ex.getMessage();
        if (message.contains("users")) {
            return User.class;
        } else if (message.contains("user_details")) {
            return UserDetail.class;
        } else if (message.contains("user_auths")) {
            return UserAuth.class;
        } else if (message.contains("roles")) {
            return UserRole.class;
        }
        return null;
    }

    /**
     * recognizing which field was filling with unique value by Annotation in {@link User} class
     */
    private String recognizeDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMessage();
        String requestMessage = "Entity with such %s already exist.";
        Class<?> targetClass = recognizeClassByException(ex);

        if (targetClass == null) {
            return "Something was wrong. Try again later please.";
        }

        Field[] declaredFields = targetClass.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation.unique() &&
                    (message.contains("(" + columnAnnotation.name() + ")") || message.contains(columnAnnotation.name().toUpperCase()))) {
                    return requestMessage.formatted(field.getName());
                }
            } else if (field.isAnnotationPresent(JoinColumn.class)) {
                JoinColumn joinColumnAnnotation = field.getAnnotation(JoinColumn.class);
                if (joinColumnAnnotation.unique() &&
                    (message.contains("(" + joinColumnAnnotation.name() + ")") || message.contains(joinColumnAnnotation.name().toUpperCase()))) {
                    return requestMessage.formatted(field.getName());
                }
            }
        }

        return "Something was wrong. Try again later please.";
    }


}
