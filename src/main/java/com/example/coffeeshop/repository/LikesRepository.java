package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LikesRepository extends JpaRepository<Likes, String> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO likes (id, uid, coffee_id) " +
            "VALUES (UUID(), :#{#likes.uid}, :#{#likes.coffeeId})",
            nativeQuery = true)
    int addLikeCoffee(Likes likes);

//    ArrayList<Likes> findAllByUid(String uid);
}
