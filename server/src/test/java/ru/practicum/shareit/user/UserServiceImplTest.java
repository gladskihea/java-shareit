package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_whenValid_thenUserSaved() {
        User user = new User(1L, "Test", "test@mail.com");
        UserDto userDto = new UserDto(null, "Test", "test@mail.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto actual = userService.createUser(userDto);

        assertEquals(1L, actual.getId());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_whenAllFieldsNotNull_thenUpdateAll() {
        Long userId = 1L;
        User existingUser = new User(userId, "Old Name", "old@mail.com");
        UserDto updateDto = new UserDto(null, "New Name", "new@mail.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        UserDto result = userService.updateUser(userId, updateDto);

        assertEquals("New Name", result.getName());
        assertEquals("new@mail.com", result.getEmail());
    }

    @Test
    void updateUser_whenOnlyNameNotNull_thenUpdateOnlyName() {
        Long userId = 1L;
        User existingUser = new User(userId, "Old Name", "old@mail.com");
        UserDto updateDto = new UserDto(null, "New Name", null);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        UserDto result = userService.updateUser(userId, updateDto);

        assertEquals("New Name", result.getName());
        assertEquals("old@mail.com", result.getEmail());
    }

    @Test
    void updateUser_whenOnlyEmailNotNull_thenUpdateOnlyEmail() {
        Long userId = 1L;
        User existingUser = new User(userId, "Old Name", "old@mail.com");
        UserDto updateDto = new UserDto(null, null, "new@mail.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        UserDto result = userService.updateUser(userId, updateDto);

        assertEquals("Old Name", result.getName());
        assertEquals("new@mail.com", result.getEmail());
    }

    @Test
    void getUserById_whenUserFound_thenReturnUser() {
        User user = new User(1L, "Test", "test@mail.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto actual = userService.getUserById(1L);
        assertEquals("Test", actual.getName());
    }

    @Test
    void getUserById_whenUserNotFound_thenThrowNotFoundException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.getUserById(99L));
    }

    @Test
    void getAllUsers_thenReturnListOfUsers() {
        User user = new User(1L, "Test", "test@mail.com");
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDto> actual = userService.getAllUsers();
        assertEquals(1, actual.size());
    }

    @Test
    void deleteUser_thenRepositoryDeleteCalled() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateUser_whenUserNotFound_thenThrowNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.updateUser(99L, new UserDto()));
    }
}