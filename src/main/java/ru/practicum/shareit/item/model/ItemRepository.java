package ru.practicum.shareit.item.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

//    List<Item> findByUserId(Integer id);
//
//    void deleteByUserIdAndId(Integer userId, Integer itemId);
//
//    List<Item> findItemByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String query, String query1);
}
