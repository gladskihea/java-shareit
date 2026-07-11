package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShareItServerTest {
    @Test
    void mainTest() {
        ShareItServer.main(new String[]{});
    }
}