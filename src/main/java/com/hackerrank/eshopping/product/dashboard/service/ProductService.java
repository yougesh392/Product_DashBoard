package com.hackerrank.eshopping.product.dashboard.service;

import com.hackerrank.eshopping.product.dashboard.DTOs.ProductDTO;
import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.model.ProductRepository;
import com.hackerrank.eshopping.product.dashboard.util.CustomComparatorsUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean addProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);

        Optional<Product> dbProduct = repository.findById(product.getId());
        if (dbProduct.isPresent()) {
            return false;
        } else {
            repository.save(product);
            return true;
        }
    }

    @Override
    public boolean updateProduct(Long productId, ProductDTO productDTO) {
        productDTO.setId(productId);
        Product product = modelMapper.map(productDTO, Product.class);
        try {
            Product productDB = repository.getOne(product.getId());
            productDB.setRetailPrice(product.getRetailPrice());
            productDB.setDiscountedPrice(product.getDiscountedPrice());
            productDB.setAvailability(product.getAvailability());
            repository.save(productDB);
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public ProductDTO getProduct(Long id) {
        Optional<Product> product = repository.findById(id);
        if (product.isPresent()) {
            return modelMapper.map(product.get(), ProductDTO.class);
        }
        return null;

    }

    @Override
    public List<ProductDTO> getByCategory(String cartegory) {
        return repository.findProductsByCategory(cartegory).stream()
                .sorted(CustomComparatorsUtil.stockComparator)
                .map(p -> modelMapper.map(p, ProductDTO.class)).
                collect(Collectors.toList());

    }

    @Override
    public List<ProductDTO> getByCategoryAndAvailability(String category, int availability) {
        boolean checkAvailability = (availability == 1);
        List<Product> products = repository.findProductByCategoryAndAvailability(category, checkAvailability);
        products.sort(CustomComparatorsUtil.categoryAndAvailabilityComparator);
        return products.stream()
                .map(p -> modelMapper.map(p, ProductDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<ProductDTO> getProducts() {
        return repository.findAll().stream()
                .sorted(CustomComparatorsUtil.idComparator)
                .map(p -> modelMapper.map(p, ProductDTO.class)).collect(Collectors.toList());
    }
}

