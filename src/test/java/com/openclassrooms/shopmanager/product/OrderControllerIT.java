package com.openclassrooms.shopmanager.product;

import com.openclassrooms.shopmanager.order.Cart;
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


}
