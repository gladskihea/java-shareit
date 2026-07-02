package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final Map<Long, Item> items = new HashMap<>();
    private long idCounter = 0L;

    @Override
    public Item save(Item item) {
        item.setId(++idCounter);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public List<Item> findAllByOwnerId(Long ownerId) {
        return items.values().stream()
                .filter(item -> item.getOwner().equals(ownerId))
                .collect(Collectors.toList());
    }

    @Override
    public Item update(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public List<Item> search(String text) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        String lower = text.toLowerCase();
        return items.values().stream()
                .filter(item -> Boolean.TRUE.equals(item.getAvailable()))
                .filter(item ->
                        (item.getName() != null && item.getName().toLowerCase().contains(lower))
                                || (item.getDescription() != null && item.getDescription().toLowerCase().contains(lower)))
                .collect(Collectors.toList());
    }
}