package com.restaurant.ordering.dto;

import java.math.BigDecimal;

/**
 * DTOs (Data Transfer Objects) are the JSON shapes the API sends and receives.
 * They're separate from the JPA entities so the database model can change
 * without breaking the API, and so internal fields never leak out.
 *
 * One menu item as returned by GET /api/menu.
 */
public record MenuItemResponse(Long id, String name, String description, BigDecimal price) {
}
