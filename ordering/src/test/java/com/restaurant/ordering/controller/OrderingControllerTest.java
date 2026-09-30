package com.restaurant.ordering.controller;

import com.restaurant.ordering.dto.MenuItemResponse;
import com.restaurant.ordering.dto.OrderLineResponse;
import com.restaurant.ordering.dto.OrderResponse;
import com.restaurant.ordering.service.OrderingService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests the HTTP layer only: URL mapping, status codes, JSON shape,
 * validation, error formatting, and CORS. @WebMvcTest starts just the web
 * slice of Spring, and the service is a mock, so no database is needed.
 */
@WebMvcTest(OrderingController.class)
class OrderingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderingService orderingService;

    private final OrderResponse sampleOrder = new OrderResponse(
            7L, OffsetDateTime.parse("2026-01-01T12:00:00Z"),
            List.of(new OrderLineResponse("Burger", 2, new BigDecimal("29.00"))),
            new BigDecimal("29.00"));

    @Test
    void getMenuReturnsJsonArray() throws Exception {
        when(orderingService.getMenu()).thenReturn(List.of(
                new MenuItemResponse(1L, "Burger", "Cheesy beef burger", new BigDecimal("14.50"))));

        mockMvc.perform(get("/api/menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Burger"))
                .andExpect(jsonPath("$[0].price").value(14.50));
    }

    @Test
    void createOrderReturns201() throws Exception {
        when(orderingService.createOrder()).thenReturn(sampleOrder);

        mockMvc.perform(post("/api/orders"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(7));
    }

    @Test
    void addItemPassesNameToService() throws Exception {
        when(orderingService.addItem(7L, "Burger")).thenReturn(sampleOrder);

        mockMvc.perform(post("/api/orders/7/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"itemName\":\"Burger\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lines[0].quantity").value(2))
                .andExpect(jsonPath("$.total").value(29.00));
    }

    @Test
    void blankItemNameIsRejectedWith400BeforeReachingService() throws Exception {
        mockMvc.perform(post("/api/orders/7/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"itemName\":\"  \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid request"));

        verify(orderingService, never()).addItem(anyLong(), any());
    }

    @Test
    void notFoundFromServiceBecomes404WithMessage() throws Exception {
        when(orderingService.getOrder(99L)).thenThrow(new EntityNotFoundException("Order not found"));

        mockMvc.perform(get("/api/orders/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Order not found"));
    }

    @Test
    void removeItemUsesDelete() throws Exception {
        when(orderingService.removeItem(7L, "Burger")).thenReturn(sampleOrder);

        mockMvc.perform(delete("/api/orders/7/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"itemName\":\"Burger\"}"))
                .andExpect(status().isOk());

        verify(orderingService).removeItem(7L, "Burger");
    }

    @Test
    void corsAllowsConfiguredFrontendOrigin() throws Exception {
        mockMvc.perform(options("/api/menu")
                        .header("Origin", "http://localhost:5173")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:5173"));
    }

    @Test
    void corsRejectsOtherOrigins() throws Exception {
        mockMvc.perform(options("/api/menu")
                        .header("Origin", "http://evil.example")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isForbidden());
    }
}
