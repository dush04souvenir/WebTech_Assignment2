package com.assignment2.question4_product_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assignment2.question4_product_api.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private List<Product> products;

    public ProductController() {
        products = new ArrayList<>();

        products.add(new Product(1L, "iPhone 14", "Apple smartphone", 1200.0, "Electronics", 5, "Apple"));
        products.add(new Product(2L, "Galaxy S23", "Samsung smartphone", 1100.0, "Electronics", 3, "Samsung"));
        products.add(new Product(3L, "MacBook Pro", "Apple laptop", 2500.0, "Computers", 2, "Apple"));
        products.add(new Product(4L, "Dell XPS", "Dell laptop", 1800.0, "Computers", 0, "Dell"));
        products.add(new Product(5L, "Sony Headphones", "Noise cancelling", 300.0, "Accessories", 10, "Sony"));
        products.add(new Product(6L, "Nike Shoes", "Running shoes", 150.0, "Fashion", 7, "Nike"));
        products.add(new Product(7L, "Adidas Jacket", "Winter jacket", 200.0, "Fashion", 0, "Adidas"));
        products.add(new Product(8L, "LG TV", "4K Smart TV", 900.0, "Electronics", 4, "LG"));
        products.add(new Product(9L, "HP Printer", "Laser printer", 400.0, "Office", 6, "HP"));
        products.add(new Product(10L, "Office Chair", "Ergonomic chair", 250.0, "Office", 8, "IKEA"));
    }

    // get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit) {

        int start = page * limit;
        int end = Math.min(start + limit, products.size());

        if (start >= products.size()) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        return ResponseEntity.ok(products.subList(start, end));
    }

    // get product by id
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {

        return products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // get products by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable String category) {

        List<Product> result = products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // get product by brand
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Product>> getByBrand(@PathVariable String brand) {

        List<Product> result = products.stream()
                .filter(p -> p.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // search by keywork
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchByKeyword(@RequestParam String keyword) {

        List<Product> result = products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase())
                        || p.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // get by price range
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {

        List<Product> result = products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // get in stock products
    @GetMapping("/in-stock")
    public ResponseEntity<List<Product>> getInStockProducts() {

        List<Product> result = products.stream()
                .filter(p -> p.getStockQuantity() > 0)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // add new product
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {

        products.add(product);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(product);
    }

    // update product
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId,
            @RequestBody Product updated) {

        for (Product product : products) {
            if (product.getProductId().equals(productId)) {

                product.setName(updated.getName());
                product.setDescription(updated.getDescription());
                product.setPrice(updated.getPrice());
                product.setCategory(updated.getCategory());
                product.setBrand(updated.getBrand());
                product.setStockQuantity(updated.getStockQuantity());

                return ResponseEntity.ok(product);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // update stock quantity
    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateStock(
            @PathVariable Long productId,
            @RequestParam int quantity) {

        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                product.setStockQuantity(quantity);
                return ResponseEntity.ok(product);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // delete product
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {

        boolean removed = products.removeIf(p -> p.getProductId().equals(productId));

        if (removed) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
