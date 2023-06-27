package com.openclassrooms.shopmanager.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

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

    @Test
    @WithMockUser(username = "admin", password = "password", roles = {"ADMIN"})
    public void testDeleteProduct() throws Exception {
        //Arrange
        Long delProductId = 1L;
        doNothing().when(productService).deleteProduct(delProductId);
        when(productService.getAllAdminProducts()).thenReturn(new ArrayList<>());

        //Act
        this.mockMvc.perform(post("/admin/deleteProduct")
                .with(csrf())
                .param("delProductId", delProductId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("productsAdmin"))
                .andExpect(model().attributeExists("products"));

        //Verify
        verify(productService, times(1)).deleteProduct(delProductId);
    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = {"ADMIN"})
    public void testProductForm() throws Exception {
        //Act
        this.mockMvc.perform(get("/admin/product"))
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = {"ADMIN"})
    public void createProduct_NoErrors_RedirectToAdminProducts() throws Exception {
        // Arrange
        ProductModel productModel = new ProductModel();
        productModel.setName("Product");
        productModel.setPrice("10.0");
        productModel.setQuantity("1");

        doNothing().when(productService).createProduct(any(ProductModel.class));

        // Act and Assert
        mockMvc.perform(post("/admin/product")
                .with(csrf())
                .flashAttr("product", productModel))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/products"));
    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = {"ADMIN"})
    public void createProduct_HasErrors_ReturnsToProductPage() throws Exception {
        // Arrange
        ProductModel productModel = new ProductModel();
        productModel.setName(""); // name is empty which would trigger a validation error

        // Act and Assert
        mockMvc.perform(post("/admin/product")
                .with(csrf())
                .flashAttr("product", productModel))
                .andExpect(status().isOk())
                .andExpect(view().name("product"));
    }
}
