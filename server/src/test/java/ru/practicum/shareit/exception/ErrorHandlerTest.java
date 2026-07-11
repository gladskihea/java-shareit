package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorHandlerTest {

    private final ErrorHandler handler = new ErrorHandler();

    @Test
    void handleNotFound() {
        NotFoundException e = new NotFoundException("Not Found");
        Map<String, String> resp = handler.handleNotFoundException(e);
        assertEquals("Not Found", resp.get("error"));
    }

    @Test
    void handleValidation() {
        ValidationException e = new ValidationException("Validation");
        Map<String, String> resp = handler.handleValidationException(e);
        assertEquals("Validation", resp.get("error"));
    }

    @Test
    void handleConflict() {
        ConflictException e = new ConflictException("Conflict");
        Map<String, String> resp = handler.handleConflictException(e);
        assertEquals("Conflict", resp.get("error"));
    }

    @Test
    void handleThrowable() {
        Throwable e = new Throwable("Error");
        Map<String, String> resp = handler.handleThrowable(e);
        assertEquals("Произошла непредвиденная ошибка сервера: Error", resp.get("error"));
    }
    @Test
    void testUtilityClasses() {
        new ru.practicum.shareit.exception.NotFoundException("test");
        new ru.practicum.shareit.exception.ValidationException("test");
        new ru.practicum.shareit.exception.ConflictException("test");
    }
}