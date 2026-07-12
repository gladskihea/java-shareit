package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RequestDtoTest {
    @Test
    void testItemRequestDto() {
        ItemRequestDto dto = new ItemRequestDto(1L, "desc", null, List.of());
        assertEquals("desc", dto.getDescription());
        assertNotNull(dto.getItems());
    }
}