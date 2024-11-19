package org.example.repository;
import java.util.List;
import org.example.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProductRepository extends MongoRepository<Product,String> {
    List<Product> findByCategories(String  category);
    List<Product> findByNameContaining(String name);
    @Query("{ 'attributes': { $elemMatch: { 'key': ?0, 'value': ?1 } } }")
    List<Product> findByAttribute(String key, String value);
    Page<Product> findAll(Pageable pageable);
    List<Product> findAllSortedByPrice(Sort sort);
}
