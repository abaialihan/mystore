package com.abai.mystore.controller;

import com.abai.mystore.entity.Product;
import com.abai.mystore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ProductRestController {

    private ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> list(){
        return productService.listAllProduct();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){
        try {
             Product product = productService.get(id);
             return new ResponseEntity<Product>(product, HttpStatus.OK);
        }catch (NoSuchElementException e){
             return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/products")
    public void add(@RequestBody Product product){
        productService.save(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> update(@RequestBody Product product,
                       @PathVariable Long id){
        try {
            Product existProduct = productService.get(id);
            productService.save(product);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable Long id){
        productService.delete(id);
    }
}
