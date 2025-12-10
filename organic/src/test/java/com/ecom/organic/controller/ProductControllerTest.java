package com.ecom.organic.controller;

import com.ecom.organic.model.Product;
import com.ecom.organic.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllProducts() throws Exception {
        Product p1 = new Product();
        p1.setId(1);
        p1.setName("Apple");

        given(service.getAllProducts()).willReturn(Arrays.asList(p1));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Apple"));
    }

    @Test
    void testGetProductById() throws Exception {
        Product p1 = new Product();
        p1.setId(1);
        p1.setName("Apple");

        given(service.getProductById(1)).willReturn(p1);

        mockMvc.perform(get("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Apple"));
    }

    @Test
    void testGetProductByIdNotFound() throws Exception {
        given(service.getProductById(1)).willReturn(null);

        mockMvc.perform(get("/api/product/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddProduct() throws Exception {
        Product p1 = new Product();
        p1.setId(1);
        p1.setName("Apple");

        MockMultipartFile productJson = new MockMultipartFile("product", "", "application/json",
                objectMapper.writeValueAsBytes(p1));
        MockMultipartFile imageFile = new MockMultipartFile("imageFile", "test.jpg", "image/jpeg", new byte[10]);

        given(service.addProduct(any(Product.class), any())).willReturn(p1);

        mockMvc.perform(multipart("/api/product")
                .file(productJson)
                .file(imageFile))
                // .contentType(MediaType.MULTIPART_FORM_DATA)
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product p1 = new Product();
        p1.setId(1);
        p1.setName("Apple Updated");

        MockMultipartFile productJson = new MockMultipartFile("product", "", "application/json",
                objectMapper.writeValueAsBytes(p1));

        given(service.updateProduct(eq(1), any(Product.class), any())).willReturn(p1);

        // Use multipart with PUT by using 'with(request -> { request.setMethod("PUT");
        // return request; })'
        // or MockMvcRequestBuilders.multipart with GET/POST hacking.
        // Spring's multipart() defaults to POST. We can override method.
        mockMvc.perform(multipart("/api/product/1")
                .file(productJson)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Product p1 = new Product();
        p1.setId(1);
        given(service.getProductById(1)).willReturn(p1);

        mockMvc.perform(delete("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }

    @Test
    void testSearchProducts() throws Exception {
        Product p1 = new Product();
        p1.setName("Apple");

        given(service.searchProducts("Apple")).willReturn(List.of(p1));

        mockMvc.perform(get("/api/products/search").param("keyword", "Apple"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Apple"));
    }
}
