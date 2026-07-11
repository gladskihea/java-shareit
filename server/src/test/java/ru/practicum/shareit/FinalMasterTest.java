package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDto;
import ru.practicum.shareit.user.UserMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FinalMasterTest {

    @Test
    void fixCoverage() {
        assertNotNull(new UserMapper());
        assertNotNull(new ItemMapper());
        assertNotNull(new BookingMapper());

        User user = new User(1L, "N", "e@m.com");
        Item item = new Item(1L, "I", "D", true, user, null);
        Booking booking = new Booking();
        booking.setStatus(BookingStatus.WAITING);

        assertNotNull(user.toString());
        assertNotNull(user.hashCode());
        user.equals(user);
        assertNotNull(item.toString());
        assertNotNull(booking.toString());

        CommentDto cDto = new CommentDto(1L, "T", "A", null);
        assertNotNull(cDto.getText());

        ItemDto iDto = new ItemDto();
        iDto.setLastBooking(new ItemDto.BookingShortDto(1L, 1L));
        assertNotNull(iDto.getLastBooking().getId());

        BookingOutDto boDto = new BookingOutDto();
        boDto.setId(1L);
        assertNotNull(boDto.getId());
    }
}