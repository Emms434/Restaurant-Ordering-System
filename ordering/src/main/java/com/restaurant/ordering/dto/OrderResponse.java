package com.restaurant.ordering.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/** A full order: its lines plus the computed total. Returned by every /orders endpoint. */
public record OrderResponse(Long id, OffsetDateTime createdAt, List<OrderLineResponse> lines, BigDecimal total) {
}
