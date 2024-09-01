package com.yash.ecom_website.service;

import com.yash.ecom_website.model.Products;
import com.yash.ecom_website.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo repo;
    public List<Products> getAllProducts() {
        return repo.findAll();
    }

    public Products getProductById(int id) {
        return repo.findById(id).get();
    }

    public Products addProduct(Products product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }

    public Products updateProduct(int id, Products product, MultipartFile imageFile) throws IOException {
        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getName());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);

    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    @Transactional
    public List<Products> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}
