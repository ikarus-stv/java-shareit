package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    Collection<Item> getItemsByOwner(Integer userId);

    @Query(" select i from Item i " +
            "where :text>'' and available and (upper(i.name) like upper(concat('%', :text, '%')) " +
            " or upper(i.description) like upper(concat('%', :text, '%')))")
    Collection<Item> getItemsByText(@Param("text") String text);

}
