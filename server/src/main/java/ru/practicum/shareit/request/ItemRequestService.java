package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemByRequestDto;
import ru.practicum.shareit.item.dto.ItemMapperByLibrary;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRequestService {
    private final ItemRequestRepository repository;
    private final ItemRequestMapper mapper;
    private final ItemRepository itemRepository;

    public ItemRequestDto createRequest(CreateItemRequestDto createItemRequestDto) {
        ItemRequest itemRequest = mapper.fromCreateDto(createItemRequestDto);
        itemRequest.setCreated(LocalDateTime.now());
        repository.save(itemRequest);
        return mapper.itemRequest2Dto(itemRequest);
    }

    public List<ItemRequestDto> getUserRequests(Integer userId) {
        List<ItemRequestDto> result = mapper.makeListOfDto(repository.getItemRequestsByRequesterOrderByCreatedDesc(userId));
        populate(result);
        return result;
    }

    private void populate(List<ItemRequestDto> list) {
        list.forEach(i -> populate(i));
    }

    private void populate(ItemRequestDto itemRequestDto) {
        List<Item> itemList = itemRepository.getByRequestId(itemRequestDto.getId());
        List<ItemByRequestDto> listDto = ItemMapperByLibrary.INSTANCE.listItem2ListRequestDto(itemList);
        itemRequestDto.setItems(listDto);
    }

    public List<ItemRequestDto> getAllRequests() {
        List<ItemRequestDto> result = mapper.makeListOfDto(repository.findAll());
        populate(result);
        return result;
    }

    public ItemRequestDto getRequestById(Integer requestId) {
        ItemRequestDto result = mapper.itemRequest2Dto(repository.findById(requestId).orElseThrow(() -> new NotFoundException("Запрос " + requestId + " не найден")));
        populate(result);
        return result;
    }
}
