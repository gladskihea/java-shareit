package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import static org.junit.jupiter.api.Assertions.*;

class ItemMapperTest {

    @Test
    void toItemDto() {
        User owner = new User(1L, "Name", "email@mail.com");
        Item item = new Item(1L, "Item", "Desc", true, owner, null);
        ItemDto dto = ItemMapper.toItemDto(item);

        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getId(), dto.getId());
    }

    @Test
    void toItem() {
        ItemDto dto = new ItemDto(1L, "Item", "Desc", true, null, null, null, null);
        Item item = ItemMapper.toItem(dto);

        assertEquals(dto.getName(), item.getName());
    }
}