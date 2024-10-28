package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Component
public class ItemRepository {
    private Map<Integer, Item> itemMap = new HashMap<>();

    private Integer maxId = 0;

    public Item createItem(Item item) {
        maxId++;
        item.setId(maxId);
        itemMap.put(maxId, item);
        return item;
    }

    public Item getItemById(Integer itemId) {
        if (!itemMap.containsKey(itemId)) {
            throw new NotFoundException("Нет итема с id=" + itemId);
        }
        return itemMap.get(itemId);
    }

    public Item updateItem(Item item) {
        if (itemMap.containsKey(item.getId())) {
            itemMap.put(item.getId(), item);
        }
        return item;
    }

    public Collection<Item> getItemsByUser(Integer userId) {
        return itemMap.values().stream().filter(item -> item.getOwner().equals(userId)).toList();
    }

    public Collection<Item> getItemsByText(String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        String upperText = text.toUpperCase();
        return itemMap.values().stream()
                .filter(item -> item.getAvailable() && (item.getName().toUpperCase().contains(upperText)
                        || item.getDescription().toUpperCase().contains(upperText)))
                .toList();

    }
}
