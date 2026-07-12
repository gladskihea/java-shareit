package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingOutDto;

import static org.junit.jupiter.api.Assertions.*;

class BookingDtoTest {
    @Test
    void testBookingOutDto() {
        BookingOutDto dto = new BookingOutDto();
        dto.setId(1L);
        dto.setStatus(BookingStatus.APPROVED);
        assertEquals(1L, dto.getId());
        assertEquals(BookingStatus.APPROVED, dto.getStatus());
    }
}