package ru.practicum.shareit;

import org.junit.jupiter.api.Test;

class ShareItServerTest {
    @Test
    void mainTest() {
        ShareItServer.main(new String[] {
                "--server.port=0",
                "--spring.datasource.url=jdbc:h2:mem:shareit;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE",
                "--spring.datasource.driver-class-name=org.h2.Driver",
                "--spring.sql.init.mode=always"
        });
    }
}