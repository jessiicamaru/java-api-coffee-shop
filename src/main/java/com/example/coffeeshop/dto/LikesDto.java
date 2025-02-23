package com.example.coffeeshop.dto;

import com.example.coffeeshop.model.Coffee;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LikesDto {
    @JsonProperty("coffees")
    private List<CoffeeDto> coffees;
}
