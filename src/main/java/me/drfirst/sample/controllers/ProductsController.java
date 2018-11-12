package me.drfirst.sample.controllers;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import me.drfirst.sample.cargo.DtoProduct;
import me.drfirst.sample.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Slf4j
@RestController
public class ProductsController {
    @Autowired private ProductsService productService;

    @RequestMapping(path = "/api/products", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<DtoProduct>> getProducts() {
        log.debug("retrieving all products");
        final List<DtoProduct> products = productService
                .findProducts()
                .stream()
                .map(DtoProduct::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @RequestMapping(path = "/api/products/{productId}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DtoProduct> getProduct(@PathVariable final String productId) {
        log.debug("retrieving product: productId = {}", productId);
        return productService
                .findProduct(productId)
                .map(product -> ResponseEntity.ok(DtoProduct.of(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
