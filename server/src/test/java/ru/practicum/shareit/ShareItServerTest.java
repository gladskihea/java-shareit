package ru.practicum.shareit;

import org.junit.jupiter.api.Test;

class ShareItServerTest {
    @Test
    void mainTest() {
        ShareItServer.main(new String[] {"--spring.profiles.active=test", "--server.port=0"});
    }
}