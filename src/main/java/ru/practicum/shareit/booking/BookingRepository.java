package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN b.booker AS u " +
            "WHERE u.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findByUserId(Integer userId);

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN b.booker AS u " +
            "WHERE u.id = ?1 " +
            "AND b.start < current_timestamp AND b.end > current_timestamp " +
            "ORDER BY b.start DESC")
    List<Booking> findCurrentByUserId(Integer userId);

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN b.booker AS u " +
            "WHERE u.id = ?1 " +
            "and b.end < current_timestamp " +
            "ORDER BY b.start DESC")
    List<Booking> findBookingByUserIdAndFinishAfterNow(Integer userId);

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN b.booker AS u " +
            "WHERE u.id = ?1 " +
            "and b.start > current_timestamp " +
            "ORDER BY b.start DESC")
    List<Booking> findBookingByUserIdAndStarBeforeNow(Integer userId);

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN b.booker AS u " +
            "WHERE u.id = ?1 " +
            "and b.status LIKE ?2 " +
            "ORDER BY b.start DESC")
    List<Booking> findBookingByUserIdAndByStatusContainingIgnoreCase(Integer userId, Status state);

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN b.item AS i " +
            "JOIN i.owner AS o " +
            "WHERE o.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findByOwnerId(Integer userId);

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN b.item AS i " +
            "JOIN i.owner AS o " +
            "WHERE o.id = ?1 " +
            "and b.start < current_timestamp and b.end > current_timestamp " +
            "ORDER BY b.start DESC")
    List<Booking> findCurrentByOwnerId(Integer userId);

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN b.item AS i " +
            "JOIN i.owner AS o " +
            "WHERE o.id = ?1 " +
            "and b.end < current_timestamp " +
            "ORDER BY b.start DESC")
    List<Booking> findPastByOwnerId(Integer userId);

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN b.item AS i " +
            "JOIN i.owner AS o " +
            "WHERE o.id = ?1 " +
            "and b.start > current_timestamp " +
            "ORDER BY b.start DESC")
    List<Booking> findBookingByOwnerIdAndStarBeforeNow(Integer userId);

    @Query("SELECT b " +
            "FROM Booking AS b " +
            "JOIN b.item AS i " +
            "JOIN i.owner AS o " +
            "WHERE o.id = ?1 " +
            "and b.status LIKE ?2 " +
            "ORDER BY b.start DESC")
    List<Booking> findBookingByOwnerIdAndByStatusContainingIgnoreCase(Integer userId, Status state);

}
