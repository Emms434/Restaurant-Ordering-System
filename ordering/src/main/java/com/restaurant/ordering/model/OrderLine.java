package com.restaurant.ordering.model;

import jakarta.persistence.*;

/**
 * One row of an order: "this menu item, this many times" (table order_lines).
 *
 * The unique constraint on (order_id, menu_item_id) means an item appears at
 * most once per order; adding the same item again bumps the quantity instead
 * of creating a duplicate line.
 *
 * The order side is LAZY (we always reach a line through its order anyway);
 * the menu item side is loaded eagerly because every response needs the
 * item's name and price.
 */
@Entity
@Table(name = "order_lines", uniqueConstraints = {
        @UniqueConstraint(name = "uq_order_line_order_item", columnNames = {"order_id", "menu_item_id"})
})
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Column(nullable = false)
    private Integer quantity;

    public Long getId() { return id; }
    public CustomerOrder getOrder() { return order; }
    public void setOrder(CustomerOrder order) { this.order = order; }
    public MenuItem getMenuItem() { return menuItem; }
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
