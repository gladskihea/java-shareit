package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BookingEnumTest {
    @Test
    void testEnums() {
        assertNotNull(BookingStatus.valueOf("WAITING"));
        assertNotNull(BookingStatus.values());

        assertNotNull(BookingState.valueOf("ALL"));
        assertNotNull(BookingState.values());
    }
}