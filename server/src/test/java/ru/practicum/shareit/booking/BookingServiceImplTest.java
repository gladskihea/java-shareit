package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private User user;
    private Item item;
    private Booking booking;

    @BeforeEach
    void setUp() {
        user = new User(1L, "User", "u@m.com");
        User owner = new User(2L, "Owner", "o@m.com");
        item = new Item(1L, "Item", "Desc", true, owner, null);
        booking = new Booking(1L, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), item, user, BookingStatus.WAITING);
    }

    @Test
    void create_whenValid_thenSaved() {
        BookingDto dto = new BookingDto(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(bookingRepository.save(any())).thenReturn(booking);

        assertNotNull(bookingService.create(1L, dto));
    }

    @Test
    void updateStatus_Success() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        BookingOutDto result = bookingService.updateStatus(2L, 1L, true);
        assertEquals(BookingStatus.APPROVED, result.getStatus());
    }

    @Test
    void updateStatus_whenNotOwner_thenThrowException() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        assertThrows(ValidationException.class, () -> bookingService.updateStatus(1L, 1L, true));
    }

    @Test
    void getById_Success() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        assertNotNull(bookingService.getById(1L, 1L));
    }

    @Test
    void getById_whenWrongUser_thenThrowException() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        assertThrows(NotFoundException.class, () -> bookingService.getById(99L, 1L));
    }

    @Test
    void testAllStatesForBooker() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        String[] states = {"ALL", "CURRENT", "PAST", "FUTURE", "WAITING", "REJECTED"};
        for (String state : states) {
            lenient().when(bookingRepository.findAllByBookerIdOrderByStartDesc(anyLong())).thenReturn(List.of(booking));
            lenient().when(bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(anyLong(), any())).thenReturn(List.of(booking));
            lenient().when(bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(anyLong(), any())).thenReturn(List.of(booking));
            lenient().when(bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(anyLong(), any())).thenReturn(List.of(booking));
            lenient().when(bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(anyLong(), any(), any())).thenReturn(List.of(booking));

            assertNotNull(bookingService.getAllByBooker(1L, state));
        }
    }

    @Test
    void testAllStatesForOwner() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        String[] states = {"ALL", "CURRENT", "PAST", "FUTURE", "WAITING", "REJECTED"};
        for (String state : states) {
            lenient().when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(anyLong())).thenReturn(List.of(booking));
            lenient().when(bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(anyLong(), any())).thenReturn(List.of(booking));
            lenient().when(bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(anyLong(), any())).thenReturn(List.of(booking));
            lenient().when(bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByStartDesc(anyLong(), any())).thenReturn(List.of(booking));
            lenient().when(bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(anyLong(), any(), any())).thenReturn(List.of(booking));

            assertNotNull(bookingService.getAllByOwner(1L, state));
        }
    }

    @Test
    void parseState_whenUnknown_thenThrowException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        assertThrows(ValidationException.class, () -> bookingService.getAllByBooker(1L, "UNKNOWN"));
    }
}