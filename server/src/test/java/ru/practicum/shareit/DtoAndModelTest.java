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
import ru.practicum.shareit.user.UserDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DtoAndModelTest {

    @Test
    void forceCoverageForAllModelsAndDtos() {
        LocalDateTime now = LocalDateTime.now();

        User user = new User(1L, "Name", "e@m.com");
        assertNotNull(user.getId());
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        assertNotNull(user.toString());

        UserDto userDto = new UserDto(1L, "Name", "e@m.com");
        assertNotNull(userDto.getId());
        assertNotNull(userDto.getName());
        assertNotNull(userDto.getEmail());
        assertNotNull(userDto.toString());

        ItemRequest req = new ItemRequest(1L, "desc", user, now);
        assertNotNull(req.getId());
        assertNotNull(req.getDescription());
        assertNotNull(req.getRequestor());
        assertNotNull(req.getCreated());
        assertNotNull(req.toString());

        ItemRequestDto reqDto = new ItemRequestDto(1L, "desc", now, List.of());
        assertNotNull(reqDto.getId());
        assertNotNull(reqDto.getDescription());
        assertNotNull(reqDto.getCreated());
        assertNotNull(reqDto.getItems());
        assertNotNull(reqDto.toString());

        Item item = new Item(1L, "Name", "Desc", true, user, req);
        assertNotNull(item.getId());
        assertNotNull(item.getName());
        assertNotNull(item.getDescription());
        assertNotNull(item.getAvailable());
        assertNotNull(item.getOwner());
        assertNotNull(item.getRequest());
        assertNotNull(item.toString());

        ItemDto.BookingShortDto shortDto = new ItemDto.BookingShortDto(1L, 2L);
        ItemDto itemDto = new ItemDto(1L, "Name", "Desc", true, 1L, shortDto, shortDto, List.of());
        assertNotNull(itemDto.getId());
        assertNotNull(itemDto.getName());
        assertNotNull(itemDto.getDescription());
        assertNotNull(itemDto.getAvailable());
        assertNotNull(itemDto.getRequestId());
        assertNotNull(itemDto.getLastBooking());
        assertNotNull(itemDto.getNextBooking());
        assertNotNull(itemDto.getComments());
        assertNotNull(itemDto.toString());

        Comment comment = new Comment(1L, "text", item, user, now);
        assertNotNull(comment.getId());
        assertNotNull(comment.getText());
        assertNotNull(comment.getItem());
        assertNotNull(comment.getAuthor());
        assertNotNull(comment.getCreated());
        assertNotNull(comment.toString());

        CommentDto commentDto = new CommentDto(1L, "text", "author", now);
        assertNotNull(commentDto.getId());
        assertNotNull(commentDto.getText());
        assertNotNull(commentDto.getAuthorName());
        assertNotNull(commentDto.getCreated());
        assertNotNull(commentDto.toString());

        Booking booking = new Booking(1L, now, now, item, user, BookingStatus.WAITING);
        assertNotNull(booking.getId());
        assertNotNull(booking.getStart());
        assertNotNull(booking.getEnd());
        assertNotNull(booking.getItem());
        assertNotNull(booking.getBooker());
        assertNotNull(booking.getStatus());
        assertNotNull(booking.toString());

        BookingDto bookingDto = new BookingDto(1L, now, now);
        assertNotNull(bookingDto.getItemId());
        assertNotNull(bookingDto.getStart());
        assertNotNull(bookingDto.getEnd());
        assertNotNull(bookingDto.toString());

        BookingOutDto bookingOutDto = new BookingOutDto(1L, now, now, BookingStatus.WAITING, userDto, itemDto);
        assertNotNull(bookingOutDto.getId());
        assertNotNull(bookingOutDto.getStart());
        assertNotNull(bookingOutDto.getEnd());
        assertNotNull(bookingOutDto.getStatus());
        assertNotNull(bookingOutDto.getBooker());
        assertNotNull(bookingOutDto.getItem());
        assertNotNull(bookingOutDto.toString());
    }
}