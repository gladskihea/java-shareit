package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + id + " не найден"));
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.isEmailExists(userDto.getEmail(), null)) {
            throw new ConflictException("Пользователь с таким email уже существует");
        }
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + id + " не найден"));

        if (userDto.getEmail() != null && userRepository.isEmailExists(userDto.getEmail(), id)) {
            throw new ConflictException("Этот email уже используется другим пользователем");
        }
        if (userDto.getName() != null) existing.setName(userDto.getName());
        if (userDto.getEmail() != null) existing.setEmail(userDto.getEmail());

        return UserMapper.toUserDto(userRepository.update(existing));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}