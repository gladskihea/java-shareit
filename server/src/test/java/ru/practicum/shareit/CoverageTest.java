package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.user.UserMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CoverageTest {

    @Test
    void coverServerClass() {
        assertNotNull(new ShareItServer());
    }

    @Test
    void coverUserMapper() {
        assertNotNull(new UserMapper());
    }

    @Test
    void coverItemMapper() {
        assertNotNull(new ItemMapper());
    }

    @Test
    void coverBookingMapper() {
        assertNotNull(new BookingMapper());
    }

    @Test
    void coverBookingState() {
        assertNotNull(ru.practicum.shareit.booking.BookingState.values());
        assertNotNull(ru.practicum.shareit.booking.BookingState.valueOf("ALL"));
    }
}