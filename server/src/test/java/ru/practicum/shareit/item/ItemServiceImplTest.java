package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.CommentRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock private ItemRepository itemRepository;
    @Mock private UserRepository userRepository;
    @Mock private BookingRepository bookingRepository;
    @Mock private CommentRepository commentRepository;
    @Mock private ItemRequestRepository requestRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    private User user;
    private Item item;

    @BeforeEach
    void setUp() {
        user = new User(1L, "Owner", "owner@mail.com");
        item = new Item(1L, "Drill", "Powerful drill", true, user, null);
    }

    @Test
    void addItem_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.save(any())).thenReturn(item);

        ItemDto result = itemService.addItem(1L, new ItemDto());
        assertEquals("Drill", result.getName());
    }

    @Test
    void updateItem_UpdateAllFields() {
        ItemDto updateDto = new ItemDto(1L, "New Name", "New Desc", false, null, null, null, null);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        ItemDto result = itemService.updateItem(1L, 1L, updateDto);

        assertEquals("New Name", result.getName());
        assertEquals("New Desc", result.getDescription());
        assertFalse(result.getAvailable());
    }

    @Test
    void updateItem_NotOwner_ThrowException() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        assertThrows(NotFoundException.class, () -> itemService.updateItem(99L, 1L, new ItemDto()));
    }

    @Test
    void getItemById_Success() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(commentRepository.findAllByItemId(anyLong())).thenReturn(List.of());
        when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(anyLong())).thenReturn(List.of());

        ItemDto result = itemService.getItemById(1L, 1L);
        assertNotNull(result);
    }

    @Test
    void searchItems_EmptyText_ReturnEmptyList() {
        List<ItemDto> result = itemService.searchItems("");
        assertTrue(result.isEmpty());
    }

    @Test
    void addComment_Success() {
        CommentDto commentDto = new CommentDto();
        commentDto.setText("Great tool");

        Booking booking = new Booking(1L, null, null, item, user, BookingStatus.APPROVED);

        Comment savedComment = new Comment();
        savedComment.setId(1L);
        savedComment.setText("Great tool");
        savedComment.setAuthor(user); // Теперь автор не null
        savedComment.setItem(item);
        savedComment.setCreated(LocalDateTime.now());

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(anyLong(), any()))
                .thenReturn(List.of(booking));
        when(commentRepository.save(any())).thenReturn(savedComment);

        CommentDto result = itemService.addComment(1L, 1L, commentDto);

        assertNotNull(result);
        assertEquals("Great tool", result.getText());
        assertEquals("Owner", result.getAuthorName());
    }

    @Test
    void addComment_NoBooking_ThrowException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(anyLong(), any()))
                .thenReturn(List.of());

        assertThrows(ValidationException.class, () -> itemService.addComment(1L, 1L, new CommentDto(null, "Text", null, null)));
    }
}