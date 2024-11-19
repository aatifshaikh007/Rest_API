package org.example.service;

import org.example.Product;
import org.example.Rating;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository=productRepository;
    }
    public Product createProduct(Product product){
        return productRepository.save(product);
    }
    public Optional<Product> getProductById(String id){
        return productRepository.findById(id);
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public Product updateProduct(String id,Product product){
        if(productRepository.existsById(id)){
            product.setId(id);
            return productRepository.save(product);
        }
        else {
            throw new RuntimeException("product not found with ID "+id);
        }
    }
    public void deleteProduct(String id){
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
        }else{
            throw new RuntimeException("product not found with ID "+id);
        }
    }
    public List<Product>searchProductByName(String name){
        if(name == null || name.trim().isEmpty()){
            return Collections.emptyList();
        }
        return productRepository.findByNameContaining(name);
    }
    public List<Product> searchProductByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return productRepository.findByCategories(category);
    }
    public List<Product> filterByAttribute(String key, String value) {
        return productRepository.findByAttribute(key, value);
    }
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    public List<Product> getAllProductsSortedByPrice(boolean ascending) {
        Sort sort = ascending ? Sort.by(Sort.Order.asc("price")) : Sort.by(Sort.Order.desc("price"));
        return productRepository.findAllSortedByPrice(sort);
    }
    public Product addRatingToProduct(String productId, Rating rating) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            if (product.getRatings() == null) {
                product.setRatings(new ArrayList<>());
            }

            product.getRatings().add(rating);
            return productRepository.save(product);
        }
        return null; // Product not found
    }

}
