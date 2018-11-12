package me.drfirst.sample.services;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import me.drfirst.sample.entities.JpaProduct;
import me.drfirst.sample.repositories.JpaProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Searches products by provided criteria (i. e. name).
 *
 */
@Slf4j
@Service
public class SearchService {
    @Autowired private JpaProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<JpaProduct> searchProductsByName(String name)  {
        log.debug("searching products: name = {}", name);
        return productRepository.findByNameContainingAllIgnoringCase(name);
    }
}
