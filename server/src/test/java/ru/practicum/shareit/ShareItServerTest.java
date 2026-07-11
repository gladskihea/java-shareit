package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:h2:mem:shareit_final_check",
        "spring.sql.init.mode=never",
        "spring.jpa.hibernate.ddl-auto=none"
})
class ShareItServerTest {
    @Test
    void contextLoads() {
    }
}