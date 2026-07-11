package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemDto;

import static org.junit.jupiter.api.Assertions.*;

class ItemDtoTest {
    @Test
    void testItemDto() {
        ItemDto dto = new ItemDto();
        dto.setName("Name");
        dto.setRequestId(1L);
        assertEquals("Name", dto.getName());
        assertEquals(1L, dto.getRequestId());
    }
}