package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public Item createItem(Integer userId, ItemCreateDto createDto) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Нет пользователя с id=" + userId));
        createDto.setOwner(userId);
        Item item = ItemMapper.itemFromCreateDto(createDto);
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Integer userId, Integer itemId, ItemUpdateDto updateDto) {
        Item item = itemRepository.getReferenceById(itemId);
        if (!item.getOwner().equals(userId)) {
            throw new InvalidUserException("");
        }

        ItemMapper.updateFromUpdateDto(item, updateDto);
        return itemRepository.save(item);
    }

    @Override
    public Item getItemById(Integer itemId) {
        return itemRepository.getReferenceById(itemId);
    }

    @Override
    public Collection<Item> getItemsByUser(Integer userId) {
        // itemRepository.findById()
        return itemRepository.getItemsByOwner(userId);
    }

    @Override
    public Collection<Item> getItemsByText(String text) {
        return itemRepository.getItemsByText(text);
    }

    @Override
    public CommentDto createComment(Integer userId, Integer itemId, CommentCreateDto createDto) {

        if (!bookingRepository.existsByBookerAndItemAndEndBefore(userId, itemId, LocalDateTime.now())) {
            throw new ValidationException("Пользователь c id = " + userId + " не брал вещь с id = " + itemId);
        }

        createDto.setItem(itemId);
        createDto.setAuthor(userId);
        Comment comment = commentMapper.fromCreateDto(createDto);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Нет пользователя с id=" + userId));

        comment.setAuthorName(user.getName());
        comment.setCreated(LocalDateTime.now());

        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public ItemDtoWide getItemWideById(Integer itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Итем с id = " + itemId + "не найден"));
        ItemDtoWide itemDtoWide = ItemMapperByLibrary.INSTANCE.toDtoWide(item);

        LocalDateTime last = bookingRepository.findLastBookingEndByItemId(itemId)
                .stream()
                .max(Comparator.naturalOrder())
                .orElse(null);
        last = null;
        itemDtoWide.setLastBooking(last);

        LocalDateTime next = bookingRepository.findNextBookingStartByItemId(itemId)
                .stream()
                .min(Comparator.naturalOrder())
                .orElse(null);

        itemDtoWide.setNextBooking(next);

        List<Comment> comments = commentRepository.findAllByItem(itemId);
        List<CommentDto> commentDtos = commentMapper.toListCommentDto(comments);
        itemDtoWide.setComments(commentDtos);

        return itemDtoWide;
    }
}
