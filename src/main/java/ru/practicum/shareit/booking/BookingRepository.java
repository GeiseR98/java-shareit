package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("SELECT b " +
            "FROM bookings AS b " +
            "JOIN b.booker AS u " +
            "WHERE u.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findByUserId(Integer userId);
}
