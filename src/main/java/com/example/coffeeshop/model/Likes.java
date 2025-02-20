package com.example.coffeeshop.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "likes")
@Data
public class Likes {
    @Id
    private String id;

    @Column(name = "uid")
    @JsonProperty("uid")
    private String uid;

    @Column(name = "coffee_id")
    @JsonProperty("coffee_id")
    private String coffeeId;
}
