package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplTest {
    @Mock private ItemRequestRepository requestRepository;
    @Mock private UserRepository userRepository;
    @Mock private ItemRepository itemRepository;
    @InjectMocks private ItemRequestServiceImpl requestService;

    @Test
    void create_Success() {
        User user = new User(1L, "Test", "t@m.com");
        ItemRequest req = new ItemRequest(1L, "Desc", user, LocalDateTime.now());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(requestRepository.save(any())).thenReturn(req);

        ItemRequestDto result = requestService.create(1L, new ItemRequestDto());
        assertNotNull(result);
        assertEquals("Desc", result.getDescription());
    }

    @Test
    void getUserRequests_Success() {
        User user = new User(1L, "Test", "t@m.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(requestRepository.findAllByRequestorIdOrderByCreatedDesc(1L)).thenReturn(List.of());

        List<ItemRequestDto> result = requestService.getUserRequests(1L);
        assertNotNull(result);
    }

    @Test
    void getAllRequests_Pagination() {
        when(requestRepository.findAllByRequestorIdNot(anyLong(), any()))
                .thenReturn(new PageImpl<>(List.of()));
        List<ItemRequestDto> result = requestService.getAllRequests(1L, 0, 10);
        assertTrue(result.isEmpty());
    }

    @Test
    void getById_Success() {
        User user = new User(1L, "T", "e@m.com");
        ItemRequest req = new ItemRequest(1L, "D", user, LocalDateTime.now());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(requestRepository.findById(anyLong())).thenReturn(Optional.of(req));

        assertNotNull(requestService.getById(1L, 1L));
    }

    @Test
    void getById_NotFound_Exception() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(requestRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> requestService.getById(1L, 1L));
    }
}