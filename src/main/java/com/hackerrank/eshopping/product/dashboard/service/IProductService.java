package com.hackerrank.eshopping.product.dashboard.service;

import com.hackerrank.eshopping.product.dashboard.DTOs.ProductDTO;
import com.hackerrank.eshopping.product.dashboard.model.Product;

import java.util.List;

public interface IProductService {
    boolean addProduct(ProductDTO product);

    boolean updateProduct(Long productId, ProductDTO productDTO);

    ProductDTO getProduct(Long id);

    List<ProductDTO> getByCategory(String category);

    List<ProductDTO> getByCategoryAndAvailability(String category, int availability);

    List<ProductDTO> getProducts();
}
