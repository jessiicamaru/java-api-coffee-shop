package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT IGNORE INTO users (uid, display_name, email, phone_number, photo_url) " +
            "VALUES (:#{#user.uid}, :#{#user.displayName}, :#{#user.email}, :#{#user.phoneNumber}, :#{#user.photoUrl})",
            nativeQuery = true)
    int insertIgnore(User user);

}
