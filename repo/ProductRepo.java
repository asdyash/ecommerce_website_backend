package com.yash.ecom_website.repo;

import com.yash.ecom_website.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Products,Integer> {
    @Query("SELECT p from Products p WHERE "+
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword ,'%')) OR " +
            "LOWER(p.brand)  LIKE LOWER(CONCAT('%' ,:keyword, '%')) OR " +
            "LOWER(p.category)  LIKE LOWER(CONCAT('%', :keyword ,'%')) ")
    List<Products> searchProducts(String keyword);


}
