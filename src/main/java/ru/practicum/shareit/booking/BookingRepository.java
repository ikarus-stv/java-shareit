package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> getByBooker(Integer booker);

    @Query("select b from Booking b " +
           " where b.item in (select i.id from Item i where i.owner = :userId)")
    List<Booking> getByOwner(@Param("userId") Integer userId);

    Boolean existsByBookerAndItemAndEndBefore(Integer bookerId, Integer itemId, LocalDateTime localDateTime);

    @Query("select b.end " +
            "from Booking as b " +
            "where b.item = :itemId " +
            "and CURRENT_TIMESTAMP > b.end")
    List<LocalDateTime> findLastBookingEndByItemId(@Param("itemId") Integer itemId);

    @Query("select b.start " +
            "from Booking as b " +
            "where b.item = :itemId " +
            "and CURRENT_TIMESTAMP < b.start")
    List<LocalDateTime> findNextBookingStartByItemId(@Param("itemId") Integer itemId);

}
