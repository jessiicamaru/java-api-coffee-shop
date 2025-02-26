package com.example.coffeeshop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Likes {
    @Id
    private String id;

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("coffee_id")
    private String coffeeId;
}