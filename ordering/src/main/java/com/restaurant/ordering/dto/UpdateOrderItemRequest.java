package com.restaurant.ordering.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateOrderItemRequest(@NotBlank String itemName) {
}
