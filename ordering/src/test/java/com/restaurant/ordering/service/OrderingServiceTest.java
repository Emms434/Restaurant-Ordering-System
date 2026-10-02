package com.restaurant.ordering.service;

import com.restaurant.ordering.dto.MenuItemResponse;
import com.restaurant.ordering.dto.OrderResponse;
import com.restaurant.ordering.model.CustomerOrder;
import com.restaurant.ordering.model.MenuItem;
import com.restaurant.ordering.repository.CustomerOrderRepository;
import com.restaurant.ordering.repository.MenuItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the business rules in OrderingService. The repositories are
 * Mockito mocks, so these run in milliseconds with no database or Spring.
 */
@ExtendWith(MockitoExtension.class)
class OrderingServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private CustomerOrderRepository customerOrderRepository;

    private OrderingService service;

    private final MenuItem burger = menuItem(1L, "Burger", "14.50");
    private final MenuItem steak = menuItem(2L, "Steak", "27.50");

    @BeforeEach
    void setUp() {
        service = new OrderingService(menuItemRepository, customerOrderRepository);
    }

    @Test
    void getMenuSortsByName() {
        when(menuItemRepository.findAll()).thenReturn(List.of(steak, burger));

        List<MenuItemResponse> menu = service.getMenu();

        assertThat(menu).extracting(MenuItemResponse::name).containsExactly("Burger", "Steak");
    }

    @Test
    void createOrderReturnsEmptyOrderWithZeroTotal() {
        when(customerOrderRepository.save(any(CustomerOrder.class))).thenAnswer(inv -> inv.getArgument(0));

        OrderResponse response = service.createOrder();

        assertThat(response.lines()).isEmpty();
        assertThat(response.total()).isEqualByComparingTo("0");
    }

    @Test
    void addingNewItemCreatesLineWithQuantityOne() {
        CustomerOrder order = orderWithId(10L);
        stubOrder(order);
        when(menuItemRepository.findByNameIgnoreCase("Burger")).thenReturn(Optional.of(burger));

        OrderResponse response = service.addItem(10L, "Burger");

        assertThat(response.lines()).hasSize(1);
        assertThat(response.lines().get(0).quantity()).isEqualTo(1);
        assertThat(response.total()).isEqualByComparingTo("14.50");
    }

    @Test
    void addingSameItemTwiceIncrementsQuantityInsteadOfDuplicatingLine() {
        CustomerOrder order = orderWithId(10L);
        stubOrder(order);
        when(menuItemRepository.findByNameIgnoreCase("Burger")).thenReturn(Optional.of(burger));

        service.addItem(10L, "Burger");
        OrderResponse response = service.addItem(10L, "Burger");

        assertThat(response.lines()).hasSize(1);
        assertThat(response.lines().get(0).quantity()).isEqualTo(2);
        assertThat(response.lines().get(0).lineTotal()).isEqualByComparingTo("29.00");
    }

    @Test
    void totalIsSumOfLineTotalsWithExactDecimalArithmetic() {
        CustomerOrder order = orderWithId(10L);
        stubOrder(order);
        when(menuItemRepository.findByNameIgnoreCase("Burger")).thenReturn(Optional.of(burger));
        when(menuItemRepository.findByNameIgnoreCase("Steak")).thenReturn(Optional.of(steak));

        service.addItem(10L, "Burger");
        service.addItem(10L, "Burger");
        OrderResponse response = service.addItem(10L, "Steak");

        assertThat(response.total()).isEqualByComparingTo("56.50");
    }

    @Test
    void itemNameIsTrimmedBeforeLookup() {
        CustomerOrder order = orderWithId(10L);
        stubOrder(order);
        when(menuItemRepository.findByNameIgnoreCase("Burger")).thenReturn(Optional.of(burger));

        OrderResponse response = service.addItem(10L, "  Burger ");

        assertThat(response.lines()).hasSize(1);
    }

    @Test
    void removingItemDecrementsQuantity() {
        CustomerOrder order = orderWithId(10L);
        stubOrder(order);
        when(menuItemRepository.findByNameIgnoreCase("Burger")).thenReturn(Optional.of(burger));
        service.addItem(10L, "Burger");
        service.addItem(10L, "Burger");

        OrderResponse response = service.removeItem(10L, "Burger");

        assertThat(response.lines().get(0).quantity()).isEqualTo(1);
    }

    @Test
    void removingLastUnitDeletesTheLine() {
        CustomerOrder order = orderWithId(10L);
        stubOrder(order);
        when(menuItemRepository.findByNameIgnoreCase("Burger")).thenReturn(Optional.of(burger));
        service.addItem(10L, "Burger");

        OrderResponse response = service.removeItem(10L, "Burger");

        assertThat(response.lines()).isEmpty();
        assertThat(order.getLines()).isEmpty();
        assertThat(response.total()).isEqualByComparingTo("0");
    }

    @Test
    void removingItemNotInOrderThrows() {
        stubOrder(orderWithId(10L));
        when(menuItemRepository.findByNameIgnoreCase("Steak")).thenReturn(Optional.of(steak));

        assertThatThrownBy(() -> service.removeItem(10L, "Steak"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Item not in order");
    }

    @Test
    void unknownOrderThrows() {
        when(customerOrderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getOrder(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Order not found");
    }

    @Test
    void unknownMenuItemThrows() {
        stubOrder(orderWithId(10L));
        when(menuItemRepository.findByNameIgnoreCase("Pizza")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.addItem(10L, "Pizza"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Item not found");
    }

    // --- helpers ---

    private void stubOrder(CustomerOrder order) {
        when(customerOrderRepository.findById(order.getId())).thenReturn(Optional.of(order));
    }

    // Entities have no id setters (the database assigns ids), so tests set them reflectively.
    private static CustomerOrder orderWithId(long id) {
        CustomerOrder order = new CustomerOrder();
        ReflectionTestUtils.setField(order, "id", id);
        return order;
    }

    private static MenuItem menuItem(long id, String name, String price) {
        MenuItem item = new MenuItem();
        ReflectionTestUtils.setField(item, "id", id);
        item.setName(name);
        item.setDescription(name + " description");
        item.setPrice(new BigDecimal(price));
        return item;
    }
}
