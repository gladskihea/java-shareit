package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.UserDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:shareit;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.sql.init.mode=always"
})
class ItemServiceIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Test
    void add_and_searchItems() {
        UserDto userDto = new UserDto(null, "Owner", "owner@mail.com");
        UserDto savedUser = userService.createUser(userDto);

        ItemDto itemDto = new ItemDto(null, "Super Drill", "Very good drill", true, null, null, null, null);
        itemService.addItem(savedUser.getId(), itemDto);

        List<ItemDto> result = itemService.searchItems("SuPEr dr");

        assertEquals(1, result.size());
        assertEquals("Super Drill", result.get(0).getName());
    }
}