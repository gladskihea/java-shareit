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

import static org.junit.jupiter.api.Assertions.*;

class FinalCoverageTest {

    @Test
    void testAllEntitiesAndDtos() {
        User user = new User(1L, "Name", "e@m.com");
        UserDto userDto = new UserDto(1L, "Name", "e@m.com");
        assertEquals(1L, user.getId());
        assertEquals("Name", userDto.getName());

        Item item = new Item(1L, "N", "D", true, user, null);
        ItemDto itemDto = new ItemDto(1L, "N", "D", true, 1L, null, null, null);
        ItemDto.BookingShortDto shortDto = new ItemDto.BookingShortDto(1L, 1L);
        itemDto.setLastBooking(shortDto);

        assertEquals("N", item.getName());
        assertNotNull(itemDto.getAvailable());
        assertEquals(1L, itemDto.getLastBooking().getId());

        Booking booking = new Booking(1L, null, null, item, user, BookingStatus.WAITING);
        BookingDto bDto = new BookingDto(1L, null, null);
        BookingOutDto bOut = new BookingOutDto(1L, null, null, BookingStatus.WAITING, userDto, itemDto);

        assertEquals(1L, booking.getId());
        assertEquals(1L, bDto.getItemId());
        assertEquals(BookingStatus.WAITING, bOut.getStatus());

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Text");
        comment.setAuthor(user);
        comment.setItem(item);
        comment.setCreated(LocalDateTime.now());

        CommentDto cDto = new CommentDto(1L, "Text", "Author", LocalDateTime.now());

        assertEquals("Text", comment.getText());
        assertEquals("Author", cDto.getAuthorName());

        ItemRequest req = ItemRequest.builder()
                .id(1L)
                .description("D")
                .requestor(user)
                .created(LocalDateTime.now())
                .build();
        ItemRequestDto reqDto = new ItemRequestDto(1L, "D", LocalDateTime.now(), List.of());

        assertEquals("D", req.getDescription());
        assertEquals(1L, reqDto.getId());
    }
}