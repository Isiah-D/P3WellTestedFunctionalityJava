package com.openclassrooms.shopmanager.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
//Try @Springboot test for this controller.
//Try to create product
@Controller
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping( value = {"/products" , "/"})
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/admin/products")
    public String getAdminProducts(Model model) {
        model.addAttribute("products", productService.getAllAdminProducts());
        return "productsAdmin";
    }


    @GetMapping("/admin/product")
    public String productForm(Model model) {
        model.addAttribute("product",new ProductModel());
        return "product";
    }

    @PostMapping("/admin/product")
    public String createProduct(@Valid @ModelAttribute("productModel") ProductModel productModel, BindingResult result)
    { log.warn("Result has errors" + result.hasErrors());

        if (!result.hasErrors()) {
            productService.createProduct(productModel);
            return "redirect:/admin/products";
        } else {
            return "product";
        }
    }

    @PostMapping("/admin/deleteProduct")
    public String deleteProduct(@RequestParam("delProductId") Long delProductId,Model model)
    {
        productService.deleteProduct(delProductId);
        model.addAttribute("products", productService.getAllAdminProducts());

        return "productsAdmin";
    }
}
