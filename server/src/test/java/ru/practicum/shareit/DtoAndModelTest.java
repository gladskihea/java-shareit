package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserDto;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DtoAndModelTest {
    @Test
    void testAllDto() {
        CommentDto commentDto = new CommentDto(1L, "text", "author", LocalDateTime.now());
        assertEquals("text", commentDto.getText());

        ItemDto itemDto = new ItemDto();
        itemDto.setName("item");
        ItemDto.BookingShortDto shortDto = new ItemDto.BookingShortDto(1L, 1L);
        itemDto.setLastBooking(shortDto);
        assertEquals("item", itemDto.getName());
        assertEquals(1L, itemDto.getLastBooking().getId());

        BookingOutDto outDto = new BookingOutDto();
        outDto.setId(10L);
        assertEquals(10L, outDto.getId());

        ItemRequestDto reqDto = new ItemRequestDto(1L, "desc", null, List.of());
        assertEquals("desc", reqDto.getDescription());

        UserDto userDto = new UserDto(1L, "N", "e@m.com");
        assertEquals("N", userDto.getName());
    }
}