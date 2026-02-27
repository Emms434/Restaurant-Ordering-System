package com.restaurant.ordering.dto;

import java.math.BigDecimal;

public record OrderLineResponse(String itemName, Integer quantity, BigDecimal lineTotal) {
}
