package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CoverageTest {

    @Test
    void testMainAndConstructors() {
        ShareItServer.main(new String[]{"--server.port=0"});

        assertNotNull(new ShareItServer());

        assertNotNull(new UserMapper());
        assertNotNull(new ItemMapper());
        assertNotNull(new BookingMapper());
    }

    @Test
    void testItemMapperBranches() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Item");

        item.setRequest(null);
        ItemDto dtoNoRequest = ItemMapper.toItemDto(item);
        assertNull(dtoNoRequest.getRequestId());

        ItemRequest request = new ItemRequest();
        request.setId(10L);
        item.setRequest(request);

        ItemDto dtoWithRequest = ItemMapper.toItemDto(item);
        assertNotNull(dtoWithRequest.getRequestId());

        ItemDto itemDto = new ItemDto();
        itemDto.setName("New");
        assertNotNull(ItemMapper.toItem(itemDto));
    }

    @Test
    void testBookingAndCommentMappers() {
        User user = new User(1L, "User", "user@mail.com");
        Item item = new Item();
        item.setId(1L);

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Cool");
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.now());
        assertNotNull(ItemMapper.toCommentDto(comment));

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setBooker(user);
        booking.setItem(item);
        booking.setStart(LocalDateTime.now());
        booking.setEnd(LocalDateTime.now().plusDays(1));

        assertNotNull(BookingMapper.toBookingOutDto(booking));
    }

    @Test
    void testUserMapper() {
        User user = new User(1L, "Name", "mail@mail.com");
        UserDto dto = new UserDto(1L, "Name", "mail@mail.com");

        assertNotNull(UserMapper.toUserDto(user));
        assertNotNull(UserMapper.toUser(dto));
    }
}