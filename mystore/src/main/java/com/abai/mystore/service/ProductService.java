package com.abai.mystore.service;

import com.abai.mystore.domain.Product;
import com.abai.mystore.reposytory.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> listAllProduct(){
        return productRepo.findAll();
    }

    public void save(Product product){
        productRepo.save(product);
    }

    public Product get(Long id){
        return productRepo.findById(id).get();
    }

    public void delete(Long id){
        productRepo.deleteById(id);
    }
}
