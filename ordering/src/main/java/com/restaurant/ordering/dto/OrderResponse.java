package com.restaurant.ordering.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record OrderResponse(Long id, OffsetDateTime createdAt, List<OrderLineResponse> lines, BigDecimal total) {
}
