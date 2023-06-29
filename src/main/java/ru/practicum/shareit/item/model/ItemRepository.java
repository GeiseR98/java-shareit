package ru.practicum.shareit.item.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByOwnerId(Integer id);

    void deleteByOwnerIdAndId(Integer userId, Integer itemId);

    List<Item> findItemByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String query, String query1);

    List<Item> findByRequestIdIn(Iterable<Integer> requestIds);

    Optional<List<Item>> findByRequestId(Integer requestId);
}
