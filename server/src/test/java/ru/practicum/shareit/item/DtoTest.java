package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {
    @Test
    void testCommentDto() {
        CommentDto dto = new CommentDto(1L, "text", "author", LocalDateTime.now());
        assertEquals("text", dto.getText());
        assertEquals("author", dto.getAuthorName());
    }

    @Test
    void testItemDtoBookingShort() {
        ItemDto.BookingShortDto shortDto = new ItemDto.BookingShortDto(1L, 2L);
        assertEquals(1L, shortDto.getId());
        assertEquals(2L, shortDto.getBookerId());
    }

    @Test
    void testItemRequestDto() {
        ItemRequestDto dto = new ItemRequestDto(1L, "desc", null, List.of());
        assertEquals(1L, dto.getId());
        assertEquals("desc", dto.getDescription());
    }
}