package com.openclassrooms.shopmanager.product;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;


    @Autowired
    private ProductService productService;

    @Test
    public void testGetProductsIntegration() {
        ResponseEntity<String> response = restTemplate.getForEntity("/products", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("<title>Products</title>"));
    }

//    @Test
//    public void testGetAdminProductsIntegration() {
//        // Given some pre-existing admin products
//        Product testProduct = new Product();
////        when(productService.getAllAdminProducts()).thenReturn(Collections.singletonList(testProduct));
//
//        // When we call the /admin/products endpoint
//        ResponseEntity<String> response = restTemplate.withBasicAuth("admin", "password").getForEntity("/admin/products", String.class);
//
//
//        // Then the status code should be 200 OK
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        // And the view should be the admin products view
//        assertTrue(response.getBody().contains("<title>Admin Products</title>"));
//    }

    @Test
    public void testGetProductForm() {
        // When we call the /admin/product endpoint for the form
        ResponseEntity<String> response = restTemplate.getForEntity("/admin/product", String.class);

        // Then the status code should be 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // And the view should be the product form view
        assertTrue(response.getBody().contains("<title>Product Form</title>"));
    }

    @Test
    public void testCreateProduct() {
        // Given a product to create
        ProductModel productModel = new ProductModel();
        productModel.setName("Test Product");

        // When we POST to the /admin/product endpoint
        HttpEntity<ProductModel> request = new HttpEntity<>(productModel);
        ResponseEntity<String> response = restTemplate.postForEntity("/admin/product", request, String.class);

        // Then the status code should be 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // And the service method should have been called
        verify(productService).createProduct(any(ProductModel.class));
    }
}
