package com.hackerrank.eshopping.product.dashboard.controller;

import com.hackerrank.eshopping.product.dashboard.DTOs.ProductDTO;
import com.hackerrank.eshopping.product.dashboard.service.IProductService;
import com.hackerrank.eshopping.product.dashboard.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {
    @Autowired
    private IProductService products;

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO){
        if(products.addProduct(productDTO)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{product_id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long product_id, @RequestBody ProductDTO productDTO){
        if(products.updateProduct(product_id,productDTO)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{product_id}")
    public ResponseEntity<?> getProducts(@PathVariable Long product_id){
        ProductDTO product = products.getProduct(product_id);
        if(product!=null){
            return new ResponseEntity<>(product,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping()
    public ResponseEntity<?> getAllProducts(){
        List<ProductDTO> productsAscendingOrdered = products.getProducts();
        return new ResponseEntity<>(productsAscendingOrdered, HttpStatus.OK);
    }
    @GetMapping(params = "category")
    public ResponseEntity<?> getProductsByCategory(@RequestParam String category){
        return new ResponseEntity<>(products.getByCategory(category), HttpStatus.OK);
    }
    @GetMapping(params={"category","availability"})
    public ResponseEntity<?> getProductsByCategoryAndAvailability(@RequestParam String category, @RequestParam int availability) throws UnsupportedEncodingException {
        String decoded = URLDecoder.decode(category, "UTF-8");
        return new ResponseEntity<>(products.getByCategoryAndAvailability(decoded,availability), HttpStatus.OK);
    }




}
