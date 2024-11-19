package org.example.service;

import org.example.Product;
import org.example.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product product);
    Optional<Product>getProductById(String id);
    List<Product>getAllProducts();
    Product updateProduct(String id,Product product);
    void deleteProduct(String id);
    List<Product> searchProductByName(String name);
    List<Product>searchProductByCategory(String category);
    List<Product> filterByAttribute(String key, String value);
    Page<Product> getAllProducts(Pageable pageable);
    List<Product> getAllProductsSortedByPrice(boolean ascending);
    Product addRatingToProduct(String productId, Rating rating);

}
