package com.restaurant.ordering.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * A dish on the menu (table menu_items). Rows are created by the Flyway seed
 * migration (V2__seed_menu.sql), not through the API.
 *
 * Names are unique so customers can order by name, and price is a
 * NUMERIC(10,2) mapped to BigDecimal to keep money exact.
 */
@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
