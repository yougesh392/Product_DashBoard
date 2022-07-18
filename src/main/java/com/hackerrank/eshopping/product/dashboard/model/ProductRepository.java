package com.hackerrank.eshopping.product.dashboard.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByCategory(String category);
    List<Product> findProductByCategoryAndAvailability(String category, Boolean availability);
}
