package ru.practicum.shareit.item.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

   List<Comment> findByItemIn(Iterable<Item> items);

   List<Comment> findAllByItemId(Integer itemId);
}