package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.UserDto;
import ru.practicum.shareit.user.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:shareit;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.sql.init.mode=always"
})
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void saveUser_and_getUserById() {
        UserDto userDto = new UserDto(null, "Integration Test", "int@mail.com");

        UserDto savedUser = userService.createUser(userDto);

        assertNotNull(savedUser.getId());

        UserDto result = userService.getUserById(savedUser.getId());

        assertEquals(savedUser.getId(), result.getId());
        assertEquals("Integration Test", result.getName());
        assertEquals("int@mail.com", result.getEmail());
    }
}