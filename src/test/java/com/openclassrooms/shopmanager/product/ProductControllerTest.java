package com.openclassrooms.shopmanager.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
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

//    private ProductService mockProductService;
//    private ProductController productController;
//    private ProductModel mockModel;

//    @Before
//    public void setUp(){
//        mockProductService = mock(ProductService.class);
//        mockModel = mock(ProductModel.class);
//        productController = new ProductController(mockProductService);
//    }
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

//    @Test
//    public void testGetProducts() {
//        // Arrange
//        List<Product> expectedProducts = Arrays.asList(new Product(), new Product());
//        when(mockProductService.getAllProducts()).thenReturn(expectedProducts);
//
//        // Act
//        String viewName = productController.getProducts(mockModel);
//
//        // Assert
//        verify(mockProductService, times(1)).getAllProducts();
//        assertEquals("products", viewName);
//    }

}
