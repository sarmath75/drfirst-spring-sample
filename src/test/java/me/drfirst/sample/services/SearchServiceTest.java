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
@SpringBootTest(classes = SearchService.class)
@ActiveProfiles("local-test")
public class SearchServiceTest {
    @MockBean private JpaProductRepository productRepository;
    @SpyBean private SearchService searchService;
    private final JpaProduct spade = JpaProduct.of("spade", "Spade", 899);
    private final JpaProduct shovel = JpaProduct.of("shovel", "Shovel", 899);
    private final List<JpaProduct> products = Lists.newArrayList(spade, shovel);

    @Test
    public void test_searchProductsByName_Found() {
        when(productRepository.findByNameContainingAllIgnoringCase("spade")).thenReturn(Lists.newArrayList(spade));

        assertEquals(Sets.newHashSet(spade), Sets.newHashSet(searchService.searchProductsByName("spade")));

        verify(productRepository, times(1)).findByNameContainingAllIgnoringCase("spade");
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void test_searchProductsByName_NotFound() {
        when(productRepository.findByNameContainingAllIgnoringCase("abracadabra")).thenReturn(Lists.newArrayList());

        assertEquals(Sets.newHashSet(), Sets.newHashSet(searchService.searchProductsByName("abracadabra")));

        verify(productRepository, times(1)).findByNameContainingAllIgnoringCase("abracadabra");
        verifyNoMoreInteractions(productRepository);
    }
}
