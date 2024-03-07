package com.ejercicios.SpringSecurity.Controller;

import com.ejercicios.SpringSecurity.Repository.ProductRepository;
import com.ejercicios.SpringSecurity.Service.ProductService;
import com.ejercicios.SpringSecurity.entity.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private final ProductService productService;
    @GetMapping
    public ResponseEntity<List<Product>> findAllProduct(){
        return productService.findAll();
    }

    @PostMapping
    public ResponseEntity<Product> createProdcut(@RequestBody @Valid Product product){
        return productService.createOne(product);
    }
}
