package com.abai.mystore.controller;

import com.abai.mystore.domain.Product;
import com.abai.mystore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> list(){
        return productService.listAllProduct();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getById(@PathVariable long id){
        try {
             Product product = productService.get(id);
             return new ResponseEntity<Product>(product, HttpStatus.OK);
        }catch (NoSuchElementException e){
             return new ResponseEntity<Product>(HttpStatus.NOT_EXTENDED);
        }
    }

    @PostMapping("/products")
    public void add(@RequestBody Product product){
        productService.save(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> update(@RequestBody Product product,
                       @PathVariable long id){
        try {
            Product existProduct = productService.get(id);
            productService.save(product);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable long id){
        productService.delete(id);
    }
}
