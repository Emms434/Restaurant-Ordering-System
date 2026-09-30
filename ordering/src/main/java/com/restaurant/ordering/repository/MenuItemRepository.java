package com.restaurant.ordering.repository;

import com.restaurant.ordering.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data builds findByNameIgnoreCase's SQL from the method name
 * (WHERE lower(name) = lower(?)), so customers can type "burger" or "Burger".
 */
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findByNameIgnoreCase(String name);
}
