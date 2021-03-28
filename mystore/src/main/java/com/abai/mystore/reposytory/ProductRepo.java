package com.abai.mystore.reposytory;

import com.abai.mystore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
