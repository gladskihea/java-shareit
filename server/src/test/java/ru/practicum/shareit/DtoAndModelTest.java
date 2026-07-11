package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DtoAndModelTest {

    @Test
    void testAllEntitiesAndDtos() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(1L, "Name", "e@m.com");
        User user2 = new User(1L, "Name", "e@m.com");

        assertEquals(user, user2);
        assertEquals(user.hashCode(), user2.hashCode());
        assertNotNull(user.toString());

        ItemRequest req = new ItemRequest(1L, "d", user, now);
        ItemRequestDto reqDto = new ItemRequestDto(1L, "d", now, List.of());
        assertNotNull(req.toString());
        assertEquals(1L, reqDto.getId());

        Item item = new Item(1L, "N", "D", true, user, req);
        ItemDto itemDto = new ItemDto(1L, "N", "D", true, 1L, null, null, List.of());
        ItemDto.BookingShortDto shortDto = new ItemDto.BookingShortDto(1L, 1L);
        itemDto.setLastBooking(shortDto);
        assertNotNull(item.toString());
        assertEquals(1L, itemDto.getLastBooking().getBookerId());

        Comment comment = new Comment(1L, "text", item, user, now);
        CommentDto commentDto = new CommentDto(1L, "text", "author", now);
        assertEquals("author", commentDto.getAuthorName());
        assertNotNull(comment.getAuthor());

        Booking booking = new Booking(1L, now, now, item, user, BookingStatus.WAITING);
        BookingDto bDto = new BookingDto(1L, now, now);
        BookingOutDto bOut = new BookingOutDto(1L, now, now, BookingStatus.WAITING, null, null);
        assertNotNull(booking.toString());
        assertNotNull(bDto.getStart());
        assertNotNull(bOut.getStatus());

        assertNotNull(new ConflictException("error").getMessage());
        assertNotNull(new ValidationException("error").getMessage());
        assertNotNull(new NotFoundException("error").getMessage());
    }
}