package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.CommentRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock private ItemRepository itemRepository;
    @Mock private UserRepository userRepository;
    @Mock private BookingRepository bookingRepository;
    @Mock private CommentRepository commentRepository;
    @Mock private ItemRequestRepository requestRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void updateItem_whenValid_thenUpdated() {
        User owner = new User(1L, "Owner", "owner@mail.com");
        Item item = new Item(1L, "Old", "Old", true, owner, null);
        ItemDto updateDto = new ItemDto(1L, "New", "New", false, null, null, null, null);

        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        ItemDto result = itemService.updateItem(1L, 1L, updateDto);
        assertEquals("New", result.getName());
        assertFalse(result.getAvailable());

        assertEquals("New", item.getName());
    }

    @Test
    void addComment_whenNoBooking_thenThrowException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(new Item()));
        when(bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(anyLong(), any())).thenReturn(List.of());

        assertThrows(ValidationException.class, () -> itemService.addComment(1L, 1L, new CommentDto(null, "text", null, null)));
    }

    @Test
    void getItemById_whenOwner_thenWithBookings() {
        User owner = new User(1L, "Owner", "owner@mail.com");
        Item item = new Item(1L, "Item", "Desc", true, owner, null);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(commentRepository.findAllByItemId(anyLong())).thenReturn(List.of());
        when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(anyLong())).thenReturn(List.of());

        ItemDto result = itemService.getItemById(1L, 1L);
        assertNotNull(result);
    }
}
