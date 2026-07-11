package ru.practicum.shareit.booking;

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

    @Test
    void createBooking_whenValid_thenSaved() {
        Long bookerId = 1L;
        User booker = new User(bookerId, "Booker", "booker@mail.com");
        User owner = new User(2L, "Owner", "owner@mail.com");
        Item item = new Item(1L, "Item", "Desc", true, owner, null);

        BookingDto dto = new BookingDto(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        Booking booking = new Booking(1L, dto.getStart(), dto.getEnd(), item, booker, BookingStatus.WAITING);

        when(userRepository.findById(bookerId)).thenReturn(Optional.of(booker));
        when(itemRepository.findById(dto.getItemId())).thenReturn(Optional.of(item));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        BookingOutDto actual = bookingService.create(bookerId, dto);

        assertNotNull(actual);
        assertEquals(BookingStatus.WAITING, actual.getStatus());
        assertEquals("Item", actual.getItem().getName());
    }

    @Test
    void createBooking_whenItemNotAvailable_thenThrowValidationException() {
        Long bookerId = 1L;
        User booker = new User(bookerId, "Booker", "booker@mail.com");
        User owner = new User(2L, "Owner", "owner@mail.com");
        Item item = new Item(1L, "Item", "Desc", false, owner, null);

        BookingDto dto = new BookingDto(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

        when(userRepository.findById(bookerId)).thenReturn(Optional.of(booker));
        when(itemRepository.findById(dto.getItemId())).thenReturn(Optional.of(item));

        assertThrows(ValidationException.class, () -> bookingService.create(bookerId, dto));
        verify(bookingRepository, never()).save(any(Booking.class));
    }
}