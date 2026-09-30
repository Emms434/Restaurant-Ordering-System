package com.restaurant.ordering.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * End-to-end tests: the full Spring context, Flyway migrations, and a real
 * PostgreSQL database (connection settings come from application.properties /
 * SPRING_DATASOURCE_* env vars; CI starts Postgres as a service container).
 *
 * Each test creates its own order, so tests don't depend on each other.
 */
@SpringBootTest
@AutoConfigureMockMvc
class OrderingApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void menuReturnsSeededItemsSortedByName() throws Exception {
        mockMvc.perform(get("/api/menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name",
                        contains("Burger", "Slapping Cheese Sandwich", "Steak", "Vanilla Sunday")));
    }

    @Test
    void newOrderIsEmpty() throws Exception {
        mockMvc.perform(post("/api/orders"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.lines", hasSize(0)))
                .andExpect(jsonPath("$.total").value(0));
    }

    @Test
    void addingItemsPersistsAndCanBeFetchedAgain() throws Exception {
        long orderId = createOrder();

        addItem(orderId, "Burger");
        addItem(orderId, "burger");   // lookup is case-insensitive, so this is the same item
        addItem(orderId, "Steak");

        // GET reads the order back in a fresh transaction, exercising the lazy-loaded lines
        mockMvc.perform(get("/api/orders/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lines", hasSize(2)))
                .andExpect(jsonPath("$.lines[0].itemName").value("Burger"))
                .andExpect(jsonPath("$.lines[0].quantity").value(2))
                .andExpect(jsonPath("$.lines[0].lineTotal").value(29.00))
                .andExpect(jsonPath("$.lines[1].itemName").value("Steak"))
                .andExpect(jsonPath("$.total").value(56.50));
    }

    @Test
    void removingItemDecrementsThenDeletesLine() throws Exception {
        long orderId = createOrder();
        addItem(orderId, "Burger");
        addItem(orderId, "Burger");

        removeItem(orderId, "Burger")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lines[0].quantity").value(1));

        removeItem(orderId, "Burger")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lines", hasSize(0)));

        mockMvc.perform(get("/api/orders/{id}", orderId))
                .andExpect(jsonPath("$.lines", hasSize(0)))
                .andExpect(jsonPath("$.total").value(0));
    }

    @Test
    void unknownOrderReturns404() throws Exception {
        mockMvc.perform(get("/api/orders/{id}", 999_999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Order not found"));
    }

    @Test
    void unknownItemReturns404() throws Exception {
        long orderId = createOrder();
        mockMvc.perform(post("/api/orders/{id}/items", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"itemName\":\"Pizza\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Item not found"));
    }

    @Test
    void removingItemNotInOrderReturns404() throws Exception {
        long orderId = createOrder();
        removeItem(orderId, "Steak")
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Item not in order"));
    }

    @Test
    void healthEndpointResponds() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk());
    }

    private long createOrder() throws Exception {
        String body = mockMvc.perform(post("/api/orders"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        JsonNode json = objectMapper.readTree(body);
        return json.get("id").asLong();
    }

    private void addItem(long orderId, String itemName) throws Exception {
        mockMvc.perform(post("/api/orders/{id}/items", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ItemBody(itemName))))
                .andExpect(status().isOk());
    }

    private org.springframework.test.web.servlet.ResultActions removeItem(long orderId, String itemName) throws Exception {
        return mockMvc.perform(delete("/api/orders/{id}/items", orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ItemBody(itemName))));
    }

    private record ItemBody(String itemName) {}
}
