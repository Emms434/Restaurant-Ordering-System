package com.restaurant.ordering.repository;

import com.restaurant.ordering.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositories are interfaces only: Spring Data JPA generates the
 * implementation at startup (save, findById, findAll, delete, ...).
 */
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
}
