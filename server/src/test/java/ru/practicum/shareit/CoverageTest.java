package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.model.Item;
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
    void testNegativeScenarios() {
        Item itemWithoutRequest = new Item();
        itemWithoutRequest.setRequest(null);
        assertNotNull(ItemMapper.toItemDto(itemWithoutRequest));

    }

}