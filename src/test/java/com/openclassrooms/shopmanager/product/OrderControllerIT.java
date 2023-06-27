package com.openclassrooms.shopmanager.product;

import com.openclassrooms.shopmanager.order.Cart;
import com.openclassrooms.shopmanager.order.Order;
import com.openclassrooms.shopmanager.order.OrderController;
import com.openclassrooms.shopmanager.order.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerIT {

    @Autowired
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        orderController = new OrderController(orderService);
    }

    @Test
    public void testGetCart() {
        // Arrange
        when(orderService.getCart()).thenReturn(new Cart());

        // Act
        String viewName = orderController.getCart(model);

        // Assert
        assertEquals("cart", viewName);
        verify(model).addAttribute(eq("cart"), any(Cart.class));
    }

    @Test
    public void testAddToCart_Success() {
        // Arrange
        Long productId = 123L;
        when(orderService.addToCart(productId)).thenReturn(true);

        // Act
        String redirectUrl = orderController.addToCart(productId);

        // Assert
        assertEquals("redirect:/order/cart", redirectUrl);
    }

    @Test
    public void testAddToCart_Failure() {
        // Arrange
        Long productId = 456L;
        when(orderService.addToCart(productId)).thenReturn(false);

        // Act
        String redirectUrl = orderController.addToCart(productId);

        // Assert
        assertEquals("redirect:/products", redirectUrl);
    }

    @Test
    public void testRemoveFromCart() {
        // Arrange
        Long productId = 123L;

        // Act
        String redirectUrl = orderController.removeFromCart(productId);

        // Assert
        assertEquals("redirect:/order/cart", redirectUrl);
    }

    @Test
    public void testGetOrderForm() {
        // Arrange
        Order order = new Order();

        // Act
        String viewName = orderController.getOrderForm(order);

        // Assert
        assertEquals("order", viewName);
    }
}