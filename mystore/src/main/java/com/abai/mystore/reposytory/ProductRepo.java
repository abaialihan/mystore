package com.abai.mystore.reposytory;

import com.abai.mystore.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
