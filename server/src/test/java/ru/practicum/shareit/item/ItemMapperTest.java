package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemMapperTest {
    @Test
    void testItemMapper() {
        User owner = new User(1L, "N", "e@m.com");
        Item item = new Item(1L, "I", "D", true, owner, null);
        ItemDto dto = ItemMapper.toItemDto(item);
        assertEquals("I", dto.getName());

        Item item2 = ItemMapper.toItem(dto);
        assertEquals("I", item2.getName());
    }

    @Test
    void toCommentDto() {
        User author = new User(1L, "Author", "a@m.com");
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Text");
        comment.setAuthor(author);
        comment.setCreated(LocalDateTime.now());

        CommentDto dto = ItemMapper.toCommentDto(comment);
        assertEquals("Author", dto.getAuthorName());
        assertEquals("Text", dto.getText());
    }
}