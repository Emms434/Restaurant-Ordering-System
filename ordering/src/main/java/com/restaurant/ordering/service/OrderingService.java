package com.restaurant.ordering.service;

import com.restaurant.ordering.dto.MenuItemResponse;
import com.restaurant.ordering.dto.OrderLineResponse;
import com.restaurant.ordering.dto.OrderResponse;
import com.restaurant.ordering.model.CustomerOrder;
import com.restaurant.ordering.model.MenuItem;
import com.restaurant.ordering.model.OrderLine;
import com.restaurant.ordering.repository.CustomerOrderRepository;
import com.restaurant.ordering.repository.MenuItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * All the business logic for the app lives here: reading the menu, creating
 * orders, and adding/removing items. The controller just translates HTTP
 * requests into calls on this class.
 *
 * Every public method is @Transactional. That matters because an order's
 * lines are loaded lazily (only when first accessed), and
 * spring.jpa.open-in-view=false means the database session closes as soon as
 * the service method returns. Anything that touches order.getLines() therefore
 * has to run inside a transaction, or Hibernate throws
 * LazyInitializationException. Methods that only read use readOnly = true,
 * which lets Hibernate skip dirty-checking.
 *
 * Changes to entities inside a transaction are saved automatically when the
 * method returns (JPA "dirty checking"), which is why addItem/removeItem never
 * call save() explicitly.
 */
@Service
public class OrderingService {
    private final MenuItemRepository menuItemRepository;
    private final CustomerOrderRepository customerOrderRepository;

    public OrderingService(MenuItemRepository menuItemRepository, CustomerOrderRepository customerOrderRepository) {
        this.menuItemRepository = menuItemRepository;
        this.customerOrderRepository = customerOrderRepository;
    }

    /** Returns every menu item, alphabetically, as API-friendly DTOs. */
    @Transactional(readOnly = true)
    public List<MenuItemResponse> getMenu() {
        return menuItemRepository.findAll().stream()
                .sorted(Comparator.comparing(MenuItem::getName))
                .map(i -> new MenuItemResponse(i.getId(), i.getName(), i.getDescription(), i.getPrice()))
                .toList();
    }

    /** Creates an empty order; the database assigns its id and createdAt is set on insert. */
    @Transactional
    public OrderResponse createOrder() {
        CustomerOrder order = customerOrderRepository.save(new CustomerOrder());
        return toResponse(order);
    }

    /** Looks up one order with its lines, or throws (mapped to HTTP 404) if it doesn't exist. */
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId) {
        return toResponse(findOrder(orderId));
    }

    /**
     * Adds one unit of a menu item to an order. If the item is already on the
     * order its quantity goes up by one; otherwise a new line is created with
     * quantity 1. Item names are matched case-insensitively ("burger" == "Burger").
     */
    @Transactional
    public OrderResponse addItem(Long orderId, String itemName) {
        CustomerOrder order = findOrder(orderId);
        MenuItem item = findMenuItem(itemName);

        OrderLine line = findLine(order, item).orElseGet(() -> {
            OrderLine newLine = new OrderLine();
            newLine.setOrder(order);
            newLine.setMenuItem(item);
            newLine.setQuantity(0);
            order.getLines().add(newLine);
            return newLine;
        });

        line.setQuantity(line.getQuantity() + 1);
        return toResponse(order);
    }

    /**
     * Removes one unit of a menu item from an order. When the quantity reaches
     * zero the whole line is removed; orphanRemoval on CustomerOrder.lines
     * then deletes that row from the database.
     */
    @Transactional
    public OrderResponse removeItem(Long orderId, String itemName) {
        CustomerOrder order = findOrder(orderId);
        MenuItem item = findMenuItem(itemName);

        OrderLine line = findLine(order, item)
                .orElseThrow(() -> new EntityNotFoundException("Item not in order"));

        int nextQty = line.getQuantity() - 1;
        if (nextQty <= 0) {
            order.getLines().remove(line);
        } else {
            line.setQuantity(nextQty);
        }
        return toResponse(order);
    }

    // --- Lookup helpers: each throws EntityNotFoundException, which
    // --- ApiExceptionHandler turns into a 404 with {"error": "..."}.

    private CustomerOrder findOrder(Long orderId) {
        return customerOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    private MenuItem findMenuItem(String itemName) {
        return menuItemRepository.findByNameIgnoreCase(itemName.trim())
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
    }

    private static java.util.Optional<OrderLine> findLine(CustomerOrder order, MenuItem item) {
        return order.getLines().stream()
                .filter(l -> l.getMenuItem().getId().equals(item.getId()))
                .findFirst();
    }

    /**
     * Converts the JPA entity into the JSON shape the frontend expects.
     * Each line's total is unit price x quantity; the order total is the sum
     * of the line totals. BigDecimal is used throughout so money never picks
     * up floating-point rounding errors.
     */
    private OrderResponse toResponse(CustomerOrder order) {
        List<OrderLineResponse> lines = order.getLines().stream()
                .map(line -> new OrderLineResponse(
                        line.getMenuItem().getName(),
                        line.getQuantity(),
                        line.getMenuItem().getPrice().multiply(BigDecimal.valueOf(line.getQuantity()))
                ))
                .toList();

        BigDecimal total = lines.stream()
                .map(OrderLineResponse::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderResponse(order.getId(), order.getCreatedAt(), lines, total);
    }
}
