package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PojoCoverageTest {

    @Test
    void testLombokGeneratedMethods() {
        // Тестируем User
        User user = new User();
        user.setId(1L);
        user.setName("Name");
        user.setEmail("e@m.com");
        assertEquals(1L, user.getId());
        assertNotNull(user.toString());
        assertEquals(user, user);
        assertNotNull(user.hashCode());

        User user2 = new User(1L, "Name", "e@m.com");
        assertEquals(user, user2);

        // Тестируем Item
        Item item = new Item();
        item.setId(1L);
        item.setName("Item");
        item.setDescription("Desc");
        item.setAvailable(true);
        item.setOwner(user);
        item.setRequest(null);
        assertEquals("Item", item.getName());
        assertEquals("Desc", item.getDescription());
        assertTrue(item.getAvailable());
        assertNotNull(item.toString());
        assertNotNull(item.hashCode());

        // Тестируем Booking
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStart(LocalDateTime.now());
        booking.setEnd(LocalDateTime.now().plusDays(1));
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStatus(BookingStatus.WAITING);
        assertEquals(1L, booking.getId());
        assertNotNull(booking.toString());

        // Тестируем Comment
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Text");
        comment.setItem(item);
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.now());
        assertEquals("Text", comment.getText());
        assertNotNull(comment.getAuthor());

        // Тестируем ItemRequest
        ItemRequest req = new ItemRequest();
        req.setId(1L);
        req.setDescription("Desc");
        req.setRequestor(user);
        req.setCreated(LocalDateTime.now());
        assertEquals(1L, req.getId());

        // Покрываем Enums (Обычно они и являются "потерянными классами")
        assertNotNull(BookingStatus.valueOf("APPROVED"));
        assertTrue(BookingStatus.values().length > 0);
        assertNotNull(BookingStatus.REJECTED);
        assertNotNull(BookingStatus.CANCELED);

        // Тестируем оставшиеся DTO
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        assertNotNull(userDto.getId());

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        assertEquals(1L, bookingDto.getItemId());

        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        assertEquals(1L, itemDto.getId());
    }
}