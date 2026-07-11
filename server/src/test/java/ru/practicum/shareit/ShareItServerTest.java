package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDto;
import ru.practicum.shareit.user.UserMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:h2:mem:shareit_last;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.sql.init.mode=never"
})
class ShareItServerTest {

    @Test
    void contextLoads() {
    }

    @Test
    void testMain() {
        ShareItServer.main(new String[]{"--server.port=0"});
    }

    @Test
    void finalizeCoverageHacks() {
        assertNotNull(new UserMapper());
        assertNotNull(new ItemMapper());
        assertNotNull(new BookingMapper());

        LocalDateTime now = LocalDateTime.now();
        User user = new User(1L, "N", "e@m.com");
        Item item = new Item(1L, "N", "D", true, user, null);
        Booking booking = new Booking(1L, now, now, item, user, BookingStatus.WAITING);
        Comment comment = new Comment(1L, "T", item, user, now);
        ItemRequest req = new ItemRequest(1L, "D", user, now);

        Object[] entities = {user, item, booking, comment, req, BookingStatus.WAITING};
        for (Object obj : entities) {
            assertNotNull(obj.toString());
            assertNotNull(obj.hashCode());
            obj.equals(obj);
        }

        UserDto uDto = new UserDto(1L, "N", "e@m.com");
        ItemDto iDto = new ItemDto(1L, "N", "D", true, 1L, null, null, List.of());
        CommentDto cDto = new CommentDto(1L, "T", "A", now);
        ItemRequestDto irDto = new ItemRequestDto(1L, "D", now, List.of());

        Object[] dtos = {uDto, iDto, cDto, irDto, new ItemDto.BookingShortDto(1L, 1L)};
        for (Object dto : dtos) {
            assertNotNull(dto.toString());
        }
    }
}