package com.cybersourcecem.web;

import com.cybersourcecem.entities.Product;
import com.cybersourcecem.models.ProductModel;
import com.cybersourcecem.services.CategoryService;
import com.cybersourcecem.services.ProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ProductRestController {
    final private ProductService productService;
    final private CategoryService categoryService;
    ProductRestController(
            final ProductService productService,
            final CategoryService categoryService
    ) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping("/products")
    void createProduct(@RequestBody @Validated ProductModel productModel){
        Product product =  new Product();
        product.setTitle(productModel.getTitle());
        product.setCategory(this.categoryService.findOne(productModel.getCategoryId()));
        product.setPrice(productModel.getPrice());
        product.setCode(productModel.getCode());

        this.productService.createProduct(product);
    }

    @GetMapping("/products")
    List<Product> findAllProduct() {
        return this.productService.findAll();
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable("id") long id) {
        this.productService.deleteProduct(id);
    }

    @DeleteMapping("/products")
    void deleteProduct() {
        this.productService.deleteProductAll();
    }

}
