package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDto;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DtoAndModelTest {

    @Test
    void testAllEntitiesAndDtos() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(1L, "Name", "e@m.com");
        ItemRequest req = new ItemRequest(1L, "d", user, now);
        Item item = new Item(1L, "N", "D", true, user, req);

        Comment comment = new Comment(1L, "text", item, user, now);
        assertEquals(1L, comment.getId());
        assertEquals("text", comment.getText());

        UserDto userDto = new UserDto(1L, "N", "e@m.com");
        ItemDto itemDto = new ItemDto(1L, "N", "D", true, 1L, null, null, List.of());
        BookingOutDto bOut = new BookingOutDto(1L, now, now, BookingStatus.WAITING, userDto, itemDto);

        assertNotNull(userDto.getEmail());
        assertNotNull(bOut.getStatus());

        assertNotNull(new ConflictException("error"));
        assertNotNull(new ValidationException("error"));
    }
}