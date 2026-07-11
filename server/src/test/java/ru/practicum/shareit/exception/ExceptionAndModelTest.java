package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.booking.Booking;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionAndModelTest {
    @Test
    void testEverything() {
        User user = new User(1L, "N", "e@m.com");
        ItemRequest req = new ItemRequest(1L, "d", user, LocalDateTime.now());
        Item item = new Item(1L, "N", "D", true, user, req);
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("txt");
        Booking booking = new Booking();
        booking.setId(1L);

        assertEquals(1L, user.getId());
        assertEquals("N", item.getName());
        assertEquals("txt", comment.getText());
        assertEquals(1L, booking.getId());

        assertNotNull(new ConflictException("error"));
        assertNotNull(new ValidationException("error"));
        assertNotNull(new NotFoundException("error"));
    }
}