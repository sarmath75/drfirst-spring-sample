package me.drfirst.sample.services;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import me.drfirst.sample.entities.JpaProduct;
import me.drfirst.sample.repositories.JpaProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductsService.class)
@ActiveProfiles("local-test")
public class ProductsServiceTest {
    @MockBean private JpaProductRepository productRepository;
    @SpyBean private ProductsService productsService;
    private final JpaProduct spade = JpaProduct.of("spade", "Spade", 899);
    private final JpaProduct shovel = JpaProduct.of("shovel", "Shovel", 899);
    private final List<JpaProduct> products = Lists.newArrayList(spade, shovel);

    @Test
    public void test_findProducts_All() {
        when(productRepository.findAll()).thenReturn(Lists.newArrayList(products));

        assertEquals(Sets.newHashSet(products), Sets.newHashSet(productsService.findProducts()));

        verify(productRepository, times(1)).findAll();
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void test_findProduct_Found() {
        when(productRepository.findById("spade")).thenReturn(Optional.of(spade));

        assertEquals(Optional.of(spade), productsService.findProduct("spade"));

        verify(productRepository, times(1)).findById("spade");
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void test_findProduct_NotFound() {
        when(productRepository.findById("n/a")).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), productsService.findProduct("n/a"));

        verify(productRepository, times(1)).findById("n/a");
        verifyNoMoreInteractions(productRepository);
    }
}
