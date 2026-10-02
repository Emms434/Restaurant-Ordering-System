package com.restaurant.ordering.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Request body for adding/removing an item: {"itemName": "Burger"}.
 * @NotBlank rejects missing or empty names with a 400 before the service runs.
 */
public record UpdateOrderItemRequest(@NotBlank String itemName) {
}
