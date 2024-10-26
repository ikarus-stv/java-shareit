package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public Item createItem(Integer userId, ItemCreateDto createDto) {

        userRepository.getUserById(userId);
        createDto.setOwner(userId);
        Item item = ItemMapper.itemFromCreateDto(createDto);
        return itemRepository.createItem(item);
    }

    @Override
    public Item updateItem(Integer userId, Integer itemId, ItemUpdateDto updateDto) {
        Item item = itemRepository.getItemById(itemId);
        if (item.getOwner() != userId) {
            throw new InvalidUserException("");
        }

        ItemMapper.updateFromUpdateDto(item, updateDto);
        return itemRepository.updateItem(item);
    }

    @Override
    public Item getItemById(Integer itemId) {
        return itemRepository.getItemById(itemId);
    }

    @Override
    public Collection<Item> getItemsByUser(Integer userId) {
        return itemRepository.getItemsByUser(userId);
    }

    @Override
    public Collection<Item> getItemsByText(String text) {
        return itemRepository.getItemsByText(text);
    }
}
