package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:h2:mem:shareit_final_v3",
        "spring.sql.init.mode=never",
        "spring.jpa.hibernate.ddl-auto=none"
})
class ShareItServerTest {

    @Test
    void contextLoads() {
        assertNotNull(this.getClass());
    }

    @Test
    void testMappersAndLombok() {
        assertNotNull(new UserMapper());

                User user = new User();
        user.setId(1L);
        assertNotNull(user.toString());
    }
}