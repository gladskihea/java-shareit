package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден"));

        if (itemDto.getName() == null || itemDto.getName().isBlank()) {
            throw new ValidationException("Название вещи не может быть пустым");
        }
        if (itemDto.getDescription() == null || itemDto.getDescription().isBlank()) {
            throw new ValidationException("Описание вещи не может быть пустым");
        }
        if (itemDto.getAvailable() == null) {
            throw new ValidationException("Статус доступности вещи должен быть указан");
        }

        Item item = ItemMapper.toItem(itemDto);
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + userId));
        item.setOwner(owner);
        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        Item existing = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с ID " + itemId + " не найдена"));

        if (!existing.getOwner().equals(userId)) {
            throw new ForbiddenException("Редактировать вещь может только её владелец");
        }

        if (itemDto.getName() != null && !itemDto.getName().isBlank()) {
            existing.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null && !itemDto.getDescription().isBlank()) {
            existing.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            existing.setAvailable(itemDto.getAvailable());
        }

        return ItemMapper.toItemDto(itemRepository.update(existing));
    }

    @Override
    public ItemDto getItemById(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с ID " + itemId + " не найдена"));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> getAllItemsByOwner(Long userId) {
        return itemRepository.findAllByOwnerId(userId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchItems(String text) {
        return itemRepository.search(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}