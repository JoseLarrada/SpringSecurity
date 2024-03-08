package com.ejercicios.SpringSecurity.Service;

import com.ejercicios.SpringSecurity.Repository.ProductRepository;
import com.ejercicios.SpringSecurity.entity.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;


    public ResponseEntity<List<Product>> findAll(){
        List<Product> products=productRepository.findAll();
        if (!products.isEmpty()){
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Product> createOne(Product product){
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product));
    }


}
