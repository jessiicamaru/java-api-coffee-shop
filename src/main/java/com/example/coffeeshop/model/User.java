package com.example.coffeeshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private String uid;
    private String email;

    @Column(name = "display_name")
    @JsonProperty("display_name")
    private String displayName;

    @Column(name = "phone_number")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Column(name = "photo_url")
    @JsonProperty("photo_url")
    private String photoUrl;

}
