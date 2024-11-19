package org.example.controller;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.example.Product;
import org.example.Rating;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService=productService;
    }
    @PostMapping
    public ResponseEntity<Product> createProduct( @Valid @RequestBody Product product){
        Product createProduct=productService.createProduct(product);
        return ResponseEntity.ok(createProduct);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") String id){
        Optional<Product>product=productService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id,@RequestBody Product product){
try{
    Product updateProduct =productService.updateProduct(id,product);
    return ResponseEntity.ok(updateProduct);
}
catch (RuntimeException e){
    return ResponseEntity.notFound().build();
}
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteproduct(@PathVariable("id") String id){
        try{

            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/filter/name")
    public ResponseEntity<List<Product>> searchProductByName(@RequestParam("name") String name) {

        List<Product> products = productService.searchProductByName(name);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(products);
    }
    @GetMapping("/filter/category")
    public ResponseEntity<List<Product>> searchProductByCategory(@RequestParam("category") String category) {
        List<Product> products = productService.searchProductByCategory(category);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(products);
    }
    @GetMapping("/filter/attribute")
    public ResponseEntity<List<Product>> filterByAttribute(
            @RequestParam("key") String key,
            @RequestParam("value") String value) {

        List<Product> products = productService.filterByAttribute(key, value);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(products);
    }
    @GetMapping("/products/sorted")
    public List<Product> getProductsSortedByPrice(@RequestParam(defaultValue = "true") boolean ascending) {
        return productService.getAllProductsSortedByPrice(ascending);
    }

    @PostMapping("/{id}/ratings")
    public ResponseEntity<Product> addRatingToProduct(
            @PathVariable("id") String productId,
            @RequestBody Rating rating) {

        Product updatedProduct = productService.addRatingToProduct(productId, rating);
        if (updatedProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedProduct);
    }












}
