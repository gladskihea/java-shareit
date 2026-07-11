package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.item.model.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ItemRequestRepository requestRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void addItem_whenUserFound_thenItemSaved() {
        Long userId = 1L;
        User user = new User(userId, "Test", "test@mail.com");
        ItemDto itemDto = new ItemDto(null, "Drill", "Simple drill", true, null, null, null, null);
        Item item = new Item(1L, "Drill", "Simple drill", true, user, null);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDto actual = itemService.addItem(userId, itemDto);

        assertEquals("Drill", actual.getName());
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void addItem_whenUserNotFound_thenThrowNotFoundException() {
        Long userId = 99L;
        ItemDto itemDto = new ItemDto();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemService.addItem(userId, itemDto));
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void updateItem_whenNotOwner_thenThrowNotFoundException() {
        Long ownerId = 1L;
        Long notOwnerId = 2L;
        User owner = new User(ownerId, "Owner", "owner@mail.com");
        Item item = new Item(1L, "Drill", "Simple drill", true, owner, null);
        ItemDto itemDto = new ItemDto(null, "Updated", null, null, null, null, null, null);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        assertThrows(NotFoundException.class, () -> itemService.updateItem(notOwnerId, 1L, itemDto));
    }

    @Test
    void searchItems_whenTextIsBlank_thenReturnEmptyList() {
        List<ItemDto> actual = itemService.searchItems("");
        assertTrue(actual.isEmpty());
        verify(itemRepository, never()).search(anyString());
    }

    @Test
    void searchItems_whenTextNotBlank_thenReturnList() {
        User user = new User(1L, "Test", "test@mail.com");
        Item item = new Item(1L, "Drill", "Simple drill", true, user, null);

        when(itemRepository.search("drill")).thenReturn(List.of(item));

        List<ItemDto> actual = itemService.searchItems("drill");

        assertEquals(1, actual.size());
        assertEquals("Drill", actual.get(0).getName());
    }
}
