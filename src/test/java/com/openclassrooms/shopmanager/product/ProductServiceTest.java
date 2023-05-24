package com.openclassrooms.shopmanager.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Take this test method as a template to write your test methods for ProductService and OrderService.
 * A test method must check if a definite method does its job:
 *
 * Naming follows this popular convention : http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html
 */


@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Before
    public void setUp(){

    }

    @Test
    public void getAllProducts_DbHasData_allDataReturned(){

        //Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("First product");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("First product");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        //Act
        List<Product> products = productService.getAllProducts();

        //Assert
        assertEquals(2, products.size());
        assertEquals(1L, products.get(0).getId() , 0);
        assertEquals(2L, products.get(1).getId() , 0);
    }

    @Test
    public void getByProductId_DbHasData_returnsOneProduct() {

        //Arrange
        Long productId = 1L;
        Product expectedProduct = new Product();
        expectedProduct.setId(productId);
        expectedProduct.setName("Test Product");
        expectedProduct.setPrice(100.0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        //Act
        Product actualProduct = productService.getByProductId(productId);

        //Assert
        assertEquals(expectedProduct, actualProduct);
    }


    @Test
    public void getAllAdminProducts_DbHasData_allDataReturned() {
        //Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Admin Product1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Admin Product2");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        //Act
        List<Product> products = productService.getAllAdminProducts();

        //Assert
        assertEquals(2, products.size());
        assertEquals(1L, products.get(0).getId() , 0);
        assertEquals(2L, products.get(1).getId() , 0);
    }


    @Test
    public void createProduct_ValidProduct_newProductCreated() {
        //Arrange
        ProductModel newProduct = new Product();
        newProduct.setId(1L);
        newProduct.setName("New Product");
        newProduct.setPrice(100.0);

        when(productRepository.save(newProduct)).thenReturn(newProduct);

        //Act
        Product createdProduct = productService.createProduct(newProduct);

        //Assert
        assertEquals(newProduct, createdProduct);
    }

    @Test
    public void deleteProduct_DbHasData_productDeleted() {
        //Arrange
        Long productId = 1L;
        doNothing().when(productRepository.deleteById(productId));

        //Act
        productService.deleteProduct(productId);

        //Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    public void updateProductQuantities_ValidUpdate_updatesApplied() {
        //Arrange
        Long productId = 1L;
        Product productToUpdate = new Product();
        productToUpdate.setId(productId);
        productToUpdate.setName("Product");
        productToUpdate.setPrice(100.0);
        productToUpdate.setQuantity(10);

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("Product");
        updatedProduct.setPrice(100.0);
        updatedProduct.setQuantity(5);

        when(productRepository.findById(productId)).thenReturn(Optional.of(productToUpdate));
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        //Act
        productService.updateProductQuantities(updatedProduct);

        //Assert
        assertEquals(updatedProduct.getQuantity(), productToUpdate.getQuantity());
    }
}
