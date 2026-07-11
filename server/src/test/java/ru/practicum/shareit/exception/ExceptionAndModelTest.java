package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.item.model.Item;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionAndModelTest {

    @Test
    void testExceptions() {
        assertNotNull(new ConflictException("Conflict"));
        assertNotNull(new ValidationException("Validation"));
        assertNotNull(new NotFoundException("Not Found"));
    }

    @Test
    void testModelGettersSetters() {
        User user = new User();
        user.setId(1L);
        user.setName("Name");
        user.setEmail("e@m.com");

        assertEquals(1L, user.getId());
        assertEquals("Name", user.getName());
        assertEquals("e@m.com", user.getEmail());

        Item item = new Item();
        item.setAvailable(true);
        assertTrue(item.getAvailable());
    }
}