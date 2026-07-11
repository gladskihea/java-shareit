package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;
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
    private User owner;
    private Item item;
    private Booking booking;

    @BeforeEach
    void setUp() {
        user = new User(1L, "User", "user@mail.com");
        owner = new User(2L, "Owner", "owner@mail.com");
        item = new Item(1L, "Item", "Desc", true, owner, null);
        booking = new Booking(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), item, user, BookingStatus.WAITING);
    }

    @Test
    void create_whenValid_thenSaved() {
        BookingDto dto = new BookingDto(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(bookingRepository.save(any())).thenReturn(booking);

        assertNotNull(bookingService.create(1L, dto));
        verify(bookingRepository, times(1)).save(any());
    }

    @Test
    void updateStatus_whenApproved() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        BookingOutDto result = bookingService.updateStatus(2L, 1L, true);

        assertEquals(BookingStatus.APPROVED, result.getStatus());
        assertEquals(BookingStatus.APPROVED, booking.getStatus());
    }

    @Test
    void updateStatus_whenNotOwner_thenThrowException() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        assertThrows(ValidationException.class, () -> bookingService.updateStatus(1L, 1L, true));
    }

    @Test
    void getById_whenValid() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        assertNotNull(bookingService.getById(1L, 1L));
    }

    @Test
    void getAllByBooker_AllStates() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByBookerIdOrderByStartDesc(anyLong())).thenReturn(List.of(booking));

        assertNotNull(bookingService.getAllByBooker(1L, "ALL"));
    }

    @Test
    void parseState_whenUnknown_thenThrowException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        assertThrows(ValidationException.class, () -> bookingService.getAllByBooker(1L, "UNKNOWN"));
    }

    @Test
    void getAllByBooker_CurrentState() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(anyLong(), any(), any()))
                .thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByBooker(1L, "CURRENT"));
    }

    @Test
    void getAllByBooker_PastState() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(anyLong(), any()))
                .thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByBooker(1L, "PAST"));
    }

    @Test
    void getAllByBooker_FutureState() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(anyLong(), any()))
                .thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByBooker(1L, "FUTURE"));
    }

    @Test
    void getAllByOwner_AllStates() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(anyLong())).thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByOwner(1L, "ALL"));

        when(bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(anyLong(), any())).thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByOwner(1L, "WAITING"));
    }

    @Test
    void getAllByOwner_CurrentState() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(anyLong(), any(), any()))
                .thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByOwner(1L, "CURRENT"));
    }

    @Test
    void getAllByOwner_PastState() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(anyLong(), any()))
                .thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByOwner(1L, "PAST"));
    }

    @Test
    void getAllByOwner_FutureState() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByStartDesc(anyLong(), any()))
                .thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByOwner(1L, "FUTURE"));
    }

    @Test
    void getAllByOwner_RejectedState() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(anyLong(), any()))
                .thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByOwner(1L, "REJECTED"));
    }

    @Test
    void getAllByBooker_StateCurrent_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(anyLong(), any(), any()))
                .thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByBooker(1L, "CURRENT"));
    }

    @Test
    void getAllByBooker_StatePast_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(anyLong(), any()))
                .thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByBooker(1L, "PAST"));
    }

    @Test
    void getAllByBooker_StateFuture_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(anyLong(), any()))
                .thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByBooker(1L, "FUTURE"));
    }
    @Test
    void getAllByOwner_AllStates() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        when(bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(anyLong(), any(), any()))
                .thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByOwner(1L, "CURRENT"));

        when(bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(anyLong(), any()))
                .thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByOwner(1L, "PAST"));

        when(bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByStartDesc(anyLong(), any()))
                .thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByOwner(1L, "FUTURE"));

        when(bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(anyLong(), any()))
                .thenReturn(List.of(booking));
        assertNotNull(bookingService.getAllByOwner(1L, "REJECTED"));
    }

}