package me.drfirst.sample.repositories;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import me.drfirst.sample.entities.JpaProduct;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
@ActiveProfiles("local-test")
public class JpaProductRepositoryTest {
    @Autowired private JpaProductRepository productRepository;
    private final JpaProduct elitebook = JpaProduct.of("elitebook", "HP EliteBook", 199999);
    private final JpaProduct macbook = JpaProduct.of("macbook", "Apple MacBook", 299999);
    private  final List<JpaProduct> products = Lists.newArrayList(macbook, elitebook);

    @Before
    public void before() {
        productRepository.saveAll(products);
    }

    @Test
    public void test_findById_Found() {
        assertEquals(Optional.of(macbook), productRepository.findById("macbook"));
        assertEquals(Optional.of(elitebook), productRepository.findById("elitebook"));
    }

    @Test
    public void test_findById_NotFound() {
        assertEquals(Optional.empty(), productRepository.findById("inspiron"));
    }

    @Test
    public void test_findByNameContainingAllIgnoringCase_Found() {
        assertEquals(
                Sets.newHashSet(macbook),
                Sets.newHashSet(productRepository.findByNameContainingAllIgnoringCase("macbook")));
        assertEquals(
                Sets.newHashSet(macbook),
                Sets.newHashSet(productRepository.findByNameContainingAllIgnoringCase("MACBOOK")));

        assertEquals(
                Sets.newHashSet(elitebook),
                Sets.newHashSet(productRepository.findByNameContainingAllIgnoringCase("elitebook")));
        assertEquals(
                Sets.newHashSet(elitebook),
                Sets.newHashSet(productRepository.findByNameContainingAllIgnoringCase("ELITEBOOK")));

        assertEquals(
                Sets.newHashSet(macbook, elitebook),
                Sets.newHashSet(productRepository.findByNameContainingAllIgnoringCase("book")));
        assertEquals(
                Sets.newHashSet(macbook, elitebook),
                Sets.newHashSet(productRepository.findByNameContainingAllIgnoringCase("BOOK")));

    }

    @Test
    public void test_findByNameContainingAllIgnoringCase_NotFound() {
        assertEquals(
                Sets.newHashSet(),
                Sets.newHashSet(productRepository.findByNameContainingAllIgnoringCase("inspiron")));
        assertEquals(
                Sets.newHashSet(),
                Sets.newHashSet(productRepository.findByNameContainingAllIgnoringCase("INSPIRON")));
    }
}
