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

@Service
public class OrderingService {
    private final MenuItemRepository menuItemRepository;
    private final CustomerOrderRepository customerOrderRepository;

    public OrderingService(MenuItemRepository menuItemRepository, CustomerOrderRepository customerOrderRepository) {
        this.menuItemRepository = menuItemRepository;
        this.customerOrderRepository = customerOrderRepository;
    }

    public List<MenuItemResponse> getMenu() {
        return menuItemRepository.findAll().stream()
                .sorted(Comparator.comparing(MenuItem::getName))
                .map(i -> new MenuItemResponse(i.getId(), i.getName(), i.getDescription(), i.getPrice()))
                .toList();
    }

    @Transactional
    public OrderResponse createOrder() {
        CustomerOrder order = customerOrderRepository.save(new CustomerOrder());
        return toResponse(order);
    }

    public OrderResponse getOrder(Long orderId) {
        CustomerOrder order = customerOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.getLines().size();
        return toResponse(order);
    }

    @Transactional
    public OrderResponse addItem(Long orderId, String itemName) {
        CustomerOrder order = customerOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        MenuItem item = menuItemRepository.findByNameIgnoreCase(itemName)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        OrderLine line = order.getLines().stream()
                .filter(l -> l.getMenuItem().getId().equals(item.getId()))
                .findFirst()
                .orElseGet(() -> {
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

    @Transactional
    public OrderResponse removeItem(Long orderId, String itemName) {
        CustomerOrder order = customerOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        MenuItem item = menuItemRepository.findByNameIgnoreCase(itemName)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        OrderLine line = order.getLines().stream()
                .filter(l -> l.getMenuItem().getId().equals(item.getId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Item not in order"));

        int nextQty = line.getQuantity() - 1;
        if (nextQty <= 0) {
            order.getLines().remove(line);
        } else {
            line.setQuantity(nextQty);
        }
        return toResponse(order);
    }

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
