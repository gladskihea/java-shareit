package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.request.ItemRequest;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ItemMapperTest {
    @Test
    void testMappingWithRequest() {
        User user = new User(1L, "N", "e@m.com");
        ItemRequest req = new ItemRequest(5L, "D", user, LocalDateTime.now());
        Item item = new Item(1L, "I", "D", true, user, req);

        ItemDto dto = ItemMapper.toItemDto(item);
        assertEquals(5L, dto.getRequestId());

        Item item2 = ItemMapper.toItem(dto);
        assertEquals("I", item2.getName());
    }
}