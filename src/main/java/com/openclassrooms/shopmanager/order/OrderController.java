package com.openclassrooms.shopmanager.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
//  SPRINGBOOT TEST for possible integeration test
@Controller
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController( OrderService orderService)
    {
        this.orderService = orderService;
    }

    @GetMapping("/order/cart")
    public String getCart(Model model)
    {
        model.addAttribute("cart", orderService.getCart());
        return "cart";
    }

    @PostMapping("/order/addToCart")
    public String addToCart(@RequestParam("productId") Long productId)
    {
        boolean success = orderService.addToCart(productId);

        if (success) {
            return "redirect:/order/cart";
        } else {
            return "redirect:/products";
        }
    }

    @PostMapping("order/removeFromCart")
    public String removeFromCart(@RequestParam Long productId)
    {
        orderService.removeFromCart(productId);

        return "redirect:/order/cart";
    }

    @GetMapping("/order")
    public String getOrderForm(Order order)
    {
        return "order";
    }

    @PostMapping("/order")
    public String createOrder(@Valid @ModelAttribute("order") Order order, BindingResult result)
    {
        if (orderService.isCartEmpty()){
            result.reject("cart.empty");
        }

        if (!result.hasErrors()) {
            orderService.createOrder(order);
            return "orderCompleted";
        } else {
            return "order";
        }
    }
}
