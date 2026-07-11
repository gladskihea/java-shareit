package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
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
}