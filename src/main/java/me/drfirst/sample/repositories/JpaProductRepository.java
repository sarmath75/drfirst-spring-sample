package me.drfirst.sample.repositories;

import java.util.List;

import me.drfirst.sample.entities.JpaProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends JpaRepository<JpaProduct, String> {
    List<JpaProduct> findByNameContainingAllIgnoringCase(String name);
}
