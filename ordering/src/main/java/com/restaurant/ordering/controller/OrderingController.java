package com.restaurant.ordering.controller;

import com.restaurant.ordering.dto.MenuItemResponse;
import com.restaurant.ordering.dto.OrderResponse;
import com.restaurant.ordering.dto.UpdateOrderItemRequest;
import com.restaurant.ordering.service.OrderingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
