package me.drfirst.sample.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import me.drfirst.sample.entities.JpaProduct;
import me.drfirst.sample.services.SearchService;
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
@WebMvcTest(SearchController.class)
@ActiveProfiles("local-test")
public class SearchControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private SearchService searchService;
    private static final JpaProduct TSHIRT = JpaProduct.of("tshirt", "T-Shirt", 999);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test_search_tshirt_Ok() throws Exception {
        when(searchService.searchProductsByName("shirt")).thenReturn(Lists.newArrayList(TSHIRT));

        mockMvc.perform(get("/api/search?name=shirt").accept(APPLICATION_JSON_UTF8_VALUE))
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
               .andExpect(content().json(
                       objectMapper.writeValueAsString(Lists.newArrayList(TSHIRT))));

        verify(searchService, times(1)).searchProductsByName("shirt");
        verifyNoMoreInteractions(searchService);
    }

}
