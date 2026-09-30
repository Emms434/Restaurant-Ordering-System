package com.restaurant.ordering.dto;

import java.math.BigDecimal;

/** One line of an order: item name, how many, and price x quantity. */
public record OrderLineResponse(String itemName, Integer quantity, BigDecimal lineTotal) {
}
