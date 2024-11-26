package ru.practicum.shareit.item;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> createItem(Integer userId, ItemCreateDto requestDto) {
        return post("", userId, requestDto);
    }

    public ResponseEntity<Object> updateItem(Integer userId, Integer itemId, ItemUpdateDto updateDto) {
        return patch("/" + itemId, userId, updateDto);
    }

    public ResponseEntity<Object> getItemsByUser(Integer userId) {
        return get("/", userId);
    }

    public ResponseEntity<Object> getItemWideById(Integer itemId) {
        return get("/" + itemId);
    }

    //@GetMapping("/search")
    public ResponseEntity<Object> getItemsByText(String text) {
        Map<String, Object> parameters = Map.of("text", text);

        return get("/search?text={text}", 0L, parameters);
    }

    // @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(Integer userId, Integer itemId, CommentCreateDto createDto) {
        return post("/" + itemId + "/comment", userId, createDto);
    }

/*
    public ResponseEntity<Object> updateUser(Integer userId, UserUpdateDto userUpdateDto) {
        return patch("/"+userId, userUpdateDto);
    }

    public ResponseEntity<Object> getUserById(Integer userId) {
        return get("/"+userId);
    }

    public ResponseEntity<Object> deleteUser(Integer userId) {
        return delete("/"+userId);
    }
*/
}
