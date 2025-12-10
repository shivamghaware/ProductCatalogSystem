package com.ecom.organic.service;

import com.ecom.organic.model.Product;
import com.ecom.organic.repo.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepo repo;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        Product p1 = new Product();
        p1.setId(1);
        p1.setName("Apple");

        Product p2 = new Product();
        p2.setId(2);
        p2.setName("Banana");

        when(repo.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> products = service.getAllProducts();
        assertEquals(2, products.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        Product p = new Product();
        p.setId(1);
        p.setName("Apple");

        when(repo.findById(1)).thenReturn(Optional.of(p));

        Product found = service.getProductById(1);
        assertNotNull(found);
        assertEquals("Apple", found.getName());
        verify(repo, times(1)).findById(1);
    }

    @Test
    void testAddProduct() throws IOException {
        Product p = new Product();
        p.setName("Apple");

        MultipartFile imageFile = mock(MultipartFile.class);
        when(imageFile.isEmpty()).thenReturn(false);
        when(imageFile.getOriginalFilename()).thenReturn("apple.jpg");
        when(imageFile.getContentType()).thenReturn("image/jpeg");
        when(imageFile.getBytes()).thenReturn(new byte[] { 1, 2, 3 });

        when(repo.save(any(Product.class))).thenReturn(p);

        Product saved = service.addProduct(p, imageFile);
        assertNotNull(saved);
        verify(repo, times(1)).save(p);

        // Check if image data was set
        assertEquals("apple.jpg", p.getImageName());
        assertEquals("image/jpeg", p.getImageType());
        assertArrayEquals(new byte[] { 1, 2, 3 }, p.getImageData());
    }

    @Test
    void testUpdateProduct() throws IOException {
        Product existing = new Product();
        existing.setId(1);
        existing.setName("Old Apple");

        Product updateInfo = new Product();
        updateInfo.setName("New Apple");
        updateInfo.setPrice(BigDecimal.TEN);

        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any(Product.class))).thenReturn(existing);

        Product updated = service.updateProduct(1, updateInfo, null);
        assertNotNull(updated);
        assertEquals("New Apple", updated.getName());
        assertEquals(BigDecimal.TEN, updated.getPrice());
        verify(repo, times(1)).save(existing);
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(repo).deleteById(1);
        service.deleteProduct(1);
        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void testSearchProducts() {
        Product p = new Product();
        p.setName("Apple");
        when(repo.searchProducts("Apple")).thenReturn(List.of(p));

        List<Product> results = service.searchProducts("Apple");
        assertEquals(1, results.size());
        assertEquals("Apple", results.get(0).getName());
    }
}
