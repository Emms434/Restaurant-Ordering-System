package com.restaurant.ordering.controller;

import com.restaurant.ordering.dto.MenuItemResponse;
import com.restaurant.ordering.dto.OrderResponse;
import com.restaurant.ordering.dto.UpdateOrderItemRequest;
import com.restaurant.ordering.service.OrderingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The REST API, mounted under /api. This class is deliberately thin: it maps
 * URLs and HTTP methods to OrderingService calls and lets Spring handle JSON
 * conversion. Validation (@Valid) happens before the method runs; errors are
 * formatted by ApiExceptionHandler.
 *
 *   GET    /api/menu                    list menu items
 *   POST   /api/orders                  create an empty order (201 Created)
 *   GET    /api/orders/{id}             fetch an order with its lines and total
 *   POST   /api/orders/{id}/items       add one of {"itemName": "..."}
 *   DELETE /api/orders/{id}/items       remove one of {"itemName": "..."}
 */
@RestController
@RequestMapping("/api")
public class OrderingController {
    private final OrderingService orderingService;

    public OrderingController(OrderingService orderingService) {
        this.orderingService = orderingService;
    }

    @GetMapping("/menu")
    public List<MenuItemResponse> getMenu() {
        return orderingService.getMenu();
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder() {
        return orderingService.createOrder();
    }

    @GetMapping("/orders/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId) {
        return orderingService.getOrder(orderId);
    }

    @PostMapping("/orders/{orderId}/items")
    public OrderResponse addItem(@PathVariable Long orderId, @RequestBody @Valid UpdateOrderItemRequest request) {
        return orderingService.addItem(orderId, request.itemName());
    }

    @DeleteMapping("/orders/{orderId}/items")
    public OrderResponse removeItem(@PathVariable Long orderId, @RequestBody @Valid UpdateOrderItemRequest request) {
        return orderingService.removeItem(orderId, request.itemName());
    }
}
