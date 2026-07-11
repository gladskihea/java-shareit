package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {
    @Test
    void testUserMapper() {
        User user = new User(1L, "Name", "e@m.com");
        UserDto dto = UserMapper.toUserDto(user);
        assertEquals(user.getName(), dto.getName());

        User user2 = UserMapper.toUser(dto);
        assertEquals(dto.getName(), user2.getName());
    }
}