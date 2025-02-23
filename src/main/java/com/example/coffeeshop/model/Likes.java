package com.example.coffeeshop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "coffee_id", referencedColumnName = "coffee_id")
    @JsonProperty("coffee_id")
    private Coffee coffee;

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    @JsonProperty("uid")
    private User user;
}
