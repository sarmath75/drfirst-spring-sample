package me.drfirst.sample.controllers;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import me.drfirst.sample.entities.JpaProduct;
import me.drfirst.sample.services.ProductsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductsController.class)
@ActiveProfiles("local-test")
public class ProductsControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private ProductsService productsService;
    private static final JpaProduct TSHIRT = JpaProduct.of("tshirt", "T-Shirt", 999);
    private static final JpaProduct POLO = JpaProduct.of("polo", "Polo", 1099);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test_getProducts_Ok() throws Exception {
        when(productsService.findProducts()).thenReturn(Lists.newArrayList(POLO, TSHIRT));

        mockMvc.perform(get("/api/products").accept(APPLICATION_JSON_UTF8_VALUE))
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
               .andExpect(content().json(
                       objectMapper.writeValueAsString(Lists.newArrayList(POLO, TSHIRT))));

        verify(productsService, times(1)).findProducts();
        verifyNoMoreInteractions(productsService);
    }

    @Test
    public void test_getProduct_Ok() throws Exception {
        when(productsService.findProduct("tshirt")).thenReturn(Optional.of(TSHIRT));

        mockMvc.perform(get("/api/products/tshirt").accept(APPLICATION_JSON_UTF8_VALUE))
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
               .andExpect(content().json(
                       objectMapper.writeValueAsString(TSHIRT)));

        verify(productsService, times(1)).findProduct("tshirt");
        verifyNoMoreInteractions(productsService);
    }

    @Test
    public void test_getProduct_NotFound() throws Exception {
        when(productsService.findProduct("abracadabra")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/abracadabra").accept(APPLICATION_JSON_UTF8_VALUE))
               .andExpect(status().isNotFound());

        verify(productsService, times(1)).findProduct("abracadabra");
        verifyNoMoreInteractions(productsService);
    }
}
