package com.openclassrooms.shopmanager.product;

import com.openclassrooms.shopmanager.order.Cart;
import com.openclassrooms.shopmanager.order.CartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.productRepository = repository;
    }

    /**
     * @return all products from the inventory
     */
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public List<Product> getAllAdminProducts() {

        return productRepository.findAllByOrderByIdDesc();
    }

    public Product getByProductId(Long productId){
        return productRepository.findById(productId).get();
    }

    public void createProduct(ProductModel productModel){
        Product product = new Product();
        product.setDescription(productModel.getDescription());
        product.setDetails(productModel.getDetails());
        product.setName(productModel.getName());
        product.setPrice(Double.parseDouble(productModel.getPrice()));
        product.setQuantity(Integer.parseInt(productModel.getQuantity()));

        productRepository.save(product);
    }

    public void deleteProduct(Long productId){
        // TODO what happens if a product has been added to a cart and has been later removed from the inventory ?
        // delete the product form the cart by using the specific method
        // => the choice is up to the student
        productRepository.deleteById(productId);
    }

    public void updateProductQuantities(Cart cart){

        for (CartLine cartLine : cart.getCartLineList()) {
            Optional<Product> productOptional = productRepository.findById(cartLine.getProduct().getId());
            if (productOptional.isPresent()){
                Product product = productOptional.get();
                product.setQuantity(product.getQuantity() - cartLine.getQuantity());
                if (product.getQuantity()<1){
                    productRepository.delete(product);
                }else {
                    productRepository.save(product);
                }
            }
        }
    }
}