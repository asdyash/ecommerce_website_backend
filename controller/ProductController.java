package com.yash.ecom_website.controller;

import com.yash.ecom_website.model.Products;
import com.yash.ecom_website.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService service;

//    @RequestMapping("/")
//    public String greet(){
//        return "Hello World";
//    }
    @GetMapping("/products")
    public ResponseEntity<List<Products>> getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(),HttpStatus.OK);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity< Products> getProductById(@PathVariable int id){
        return new ResponseEntity<>(service.getProductById(id), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestPart Products product,
                                        @RequestPart MultipartFile imageFile ){
        try {
            Products product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
    @GetMapping("/products/{productId}/image")
    public ResponseEntity<byte[]> getImageById(@PathVariable int productId){
            Products product=service.getProductById(productId);
            byte[] imageFile=product.getImageData();
            return  ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType()))
                    .body(imageFile);

    }
    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id,@RequestPart Products product,
                                        @RequestPart MultipartFile imageFile ) throws IOException {
        Products product1=null;
        try{
            product1=service.updateProduct(id,product,imageFile);
        }
        catch (Exception e){
            return  new ResponseEntity<>("Failed to update!! ",HttpStatus.BAD_REQUEST);
        }
        if(product1!=null){
            return  new ResponseEntity<>("Updated!!",HttpStatus.OK);
        }
        else {
            return  new ResponseEntity<>("Failed to update!! ",HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Products product=service.getProductById(id);
        if(product!=null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted!!",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Product not found!!",HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/products/search")
    public ResponseEntity<List<Products>> searchProducts(@RequestParam String keyword){
        System.out.println("Searching with - "+keyword);
        List<Products> products=service.searchProducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

}
