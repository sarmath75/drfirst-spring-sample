package me.drfirst.sample.services;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import me.drfirst.sample.entities.JpaProduct;
import me.drfirst.sample.repositories.JpaProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Products CRUD operations.
 *
 */
@Slf4j
@Service
public class ProductsService {
    @Autowired private JpaProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<JpaProduct> findProducts()  {
        log.debug("fetching all products");
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<JpaProduct> findProduct(String productId)  {
        log.debug("fetching product: productId = {}", productId);
        return productRepository.findById(productId);
    }
}
