package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplTest {

    @Mock
    private ItemRequestRepository requestRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemRequestServiceImpl requestService;

    @Test
    void create_whenValid_thenSaved() {
        Long userId = 1L;
        User user = new User(userId, "Test", "test@mail.com");
        ItemRequestDto dto = new ItemRequestDto(null, "Need a drill", null, null);
        ItemRequest request = new ItemRequest(1L, "Need a drill", user, LocalDateTime.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(requestRepository.save(any(ItemRequest.class))).thenReturn(request);

        ItemRequestDto actual = requestService.create(userId, dto);

        assertNotNull(actual);
        assertEquals("Need a drill", actual.getDescription());
        verify(requestRepository, times(1)).save(any(ItemRequest.class));
    }

    @Test
    void getUserRequests_thenReturnList() {
        Long userId = 1L;
        User user = new User(userId, "Test", "test@mail.com");
        ItemRequest request = new ItemRequest(1L, "Need a drill", user, LocalDateTime.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(requestRepository.findAllByRequestorIdOrderByCreatedDesc(userId)).thenReturn(List.of(request));
        when(itemRepository.findAllByRequestId(anyLong())).thenReturn(List.of());

        List<ItemRequestDto> actual = requestService.getUserRequests(userId);

        assertEquals(1, actual.size());
        assertEquals("Need a drill", actual.get(0).getDescription());
    }
}