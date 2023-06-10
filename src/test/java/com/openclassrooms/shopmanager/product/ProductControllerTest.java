package com.openclassrooms.shopmanager.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
//@ContextConfiguration(classes = {ProductService.class})
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    //FIXME authentication
    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        ProductModel testProduct = new ProductModel();
        ProductService mockService = mock(ProductService.class);
        doNothing().when(mockService).createProduct(testProduct);
        this.mockMvc.perform(post("/admin/product")
                .with(user("admin").password("password").roles("ADMIN"))
                .with(csrf())
                .flashAttr("productModel", testProduct))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(model().hasErrors());


    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = {"ADMIN"})
    public void testGetProducts() throws Exception {
        //Arrange
        Product testProduct = new Product();
        when(productService.getAllProducts()).thenReturn(Collections.singletonList(testProduct));

        //Act
        this.mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attributeExists("products"));

        //Verify
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = {"ADMIN"})
    public void testGetAdminProducts() throws Exception {
        //Arrange
        Product testProduct = new Product();
        when(productService.getAllAdminProducts()).thenReturn(Collections.singletonList(testProduct));

        //Act
        this.mockMvc.perform(get("/admin/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("productsAdmin"))
                .andExpect(model().attributeExists("products"));

        //Verify
        verify(productService, times(1)).getAllAdminProducts();
    }
}
