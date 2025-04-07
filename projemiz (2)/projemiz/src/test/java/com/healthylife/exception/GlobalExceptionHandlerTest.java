package com.healthylife.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleResourceNotFoundException_ShouldReturnNotFoundStatus() {
        // given
        String errorMessage = "Kaynak bulunamadı";
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

        // when
        ResponseEntity<?> response = exceptionHandler.handleResourceNotFoundException(exception);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals(errorMessage, body.get("error"));
    }

    @Test
    void handleValidationExceptions_ShouldReturnBadRequestStatus() {
        // given
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        List<FieldError> fieldErrors = Arrays.asList(
            new FieldError("object", "field1", "Geçersiz değer"),
            new FieldError("object", "field2", "Boş olamaz")
        );

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(new ArrayList<>(fieldErrors));

        // when
        ResponseEntity<?> response = exceptionHandler.handleValidationExceptions(exception);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals("Geçersiz değer", body.get("field1"));
        assertEquals("Boş olamaz", body.get("field2"));
    }

    @Test
    void handleConstraintViolationException_ShouldReturnBadRequestStatus() {
        // given
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        when(violation.getPropertyPath()).thenReturn(mock(javax.validation.Path.class));
        when(violation.getPropertyPath().toString()).thenReturn("field");
        when(violation.getMessage()).thenReturn("Geçersiz değer");
        violations.add(violation);

        ConstraintViolationException exception = new ConstraintViolationException("message", violations);

        // when
        ResponseEntity<?> response = exceptionHandler.handleConstraintViolationException(exception);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals("Geçersiz değer", body.get("field"));
    }

    @Test
    void handleAccessDeniedException_ShouldReturnForbiddenStatus() {
        // given
        AccessDeniedException exception = new AccessDeniedException("Erişim reddedildi");

        // when
        ResponseEntity<?> response = exceptionHandler.handleAccessDeniedException(exception);

        // then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals("Bu işlem için yetkiniz bulunmamaktadır.", body.get("error"));
    }

    @Test
    void handleBadCredentialsException_ShouldReturnUnauthorizedStatus() {
        // given
        BadCredentialsException exception = new BadCredentialsException("Geçersiz kimlik bilgileri");

        // when
        ResponseEntity<?> response = exceptionHandler.handleBadCredentialsException(exception);

        // then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals("Geçersiz kullanıcı adı veya şifre.", body.get("error"));
    }

    @Test
    void handleUsernameNotFoundException_ShouldReturnNotFoundStatus() {
        // given
        String errorMessage = "Kullanıcı bulunamadı";
        UsernameNotFoundException exception = new UsernameNotFoundException(errorMessage);

        // when
        ResponseEntity<?> response = exceptionHandler.handleUsernameNotFoundException(exception);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals(errorMessage, body.get("error"));
    }

    @Test
    void handleGlobalException_ShouldReturnInternalServerErrorStatus() {
        // given
        Exception exception = new RuntimeException("Beklenmeyen hata");

        // when
        ResponseEntity<?> response = exceptionHandler.handleGlobalException(exception);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals("Bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.", body.get("error"));
    }
} 