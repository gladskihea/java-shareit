package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BookingMapperTest {
    @Test
    void toBookingOutDto() {
        User user = new User(1L, "Name", "email@mail.com");
        Item item = new Item(1L, "Item", "Desc", true, user, null);
        Booking booking = new Booking(1L, LocalDateTime.now(), LocalDateTime.now(), item, user, BookingStatus.WAITING);

        BookingOutDto dto = BookingMapper.toBookingOutDto(booking);

        assertNotNull(dto);
        assertEquals(booking.getId(), dto.getId());
    }
}