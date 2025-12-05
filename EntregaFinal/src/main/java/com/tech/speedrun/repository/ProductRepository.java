package com.tech.speedrun.repository;

import com.tech.speedrun.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase (String name);
    List<Product> findByCategoryContainingIgnoreCase (String category);
    List<Product> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase (String name, String category);


}
