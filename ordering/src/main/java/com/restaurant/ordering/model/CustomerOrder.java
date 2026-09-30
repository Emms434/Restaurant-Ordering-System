package com.restaurant.ordering.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * One customer's order (table customer_orders). An order is just an id, a
 * creation time, and a list of lines; the total is computed on the fly in
 * OrderingService rather than stored, so it can never drift out of sync.
 *
 * The lines relationship:
 *  - mappedBy = "order": OrderLine.order owns the foreign key (order_id).
 *  - cascade = ALL: saving an order also saves any new lines added to it.
 *  - orphanRemoval = true: removing a line from this list deletes its row.
 *  - @OrderBy("id"): lines come back in the order they were first added,
 *    so the order summary is stable between requests.
 * The collection is lazy by default, so it must be read inside a transaction.
 */
@Entity
@Table(name = "customer_orders")
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id")
    private List<OrderLine> lines = new ArrayList<>();

    // Runs right before the first INSERT, stamping the creation time.
    @PrePersist
    public void onCreate() {
        createdAt = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public List<OrderLine> getLines() { return lines; }
}
