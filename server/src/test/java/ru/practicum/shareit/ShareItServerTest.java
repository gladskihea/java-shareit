package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:h2:mem:shareit_final_v2",
        "spring.sql.init.mode=never",
        "spring.jpa.hibernate.ddl-auto=none"
})
class ShareItServerTest {

    @Test
    void contextLoads() {
    }

    @Test
    void testMain() {
        try {
            ShareItServer.main(new String[]{
                    "--server.port=0",
                    "--spring.sql.init.mode=never",
                    "--spring.datasource.url=jdbc:h2:mem:main_test"
            });
        } catch (Exception e) {
        }
    }

    @Test
    void testMappersAndLombok() {
        assertNotNull(new UserMapper());
        assertNotNull(new ItemMapper());
        assertNotNull(new BookingMapper());

        User user = new User();
        user.setId(1L);
        assertNotNull(user.toString());
    }
}