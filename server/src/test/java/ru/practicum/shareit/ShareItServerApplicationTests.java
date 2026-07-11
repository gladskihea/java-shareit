package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Это заставит Spring искать application-test.properties, если захочешь
class ShareItServerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void mainTest() {
        ShareItServer.main(new String[] {});
    }
}