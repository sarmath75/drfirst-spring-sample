package me.drfirst.sample.controllers;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import me.drfirst.sample.cargo.DtoProduct;
import me.drfirst.sample.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Searches products by name.
 *
 */
@Slf4j
@Controller
public class SearchController {
    @Autowired private SearchService searchService;

    @RequestMapping(path = "/api/search", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<DtoProduct>> search(@RequestParam(name = "name") String name) {
        log.debug("searching products: name = {}", name);
        final List<DtoProduct> products = searchService
                .searchProductsByName(name)
                .stream()
                .map(DtoProduct::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

}
