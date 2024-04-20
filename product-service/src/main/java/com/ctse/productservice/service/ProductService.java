package com.ctse.productservice.service;

import com.ctse.productservice.dto.ProductRequest;
import com.ctse.productservice.dto.ProductResponse;
import com.ctse.productservice.model.Product;
import com.ctse.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        Product createdProduct = productRepository.save(product);
        return mapper.map(createdProduct, ProductResponse.class);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id).orElse(null);
        return mapper.map(product, ProductResponse.class);
    }

    public ProductResponse updateProductById(String id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return null;
        if (productRequest.getName() != null && !productRequest.getName().isEmpty()) {
            product.setName(productRequest.getName());
        }
        if (productRequest.getDescription() != null && !productRequest.getDescription().isEmpty()) {
            product.setDescription(productRequest.getDescription());
        }
        if (productRequest.getPrice() != null) {
            product.setPrice(productRequest.getPrice());
        }
        Product updatedProduct = productRepository.save(product);
        return mapper.map(updatedProduct, ProductResponse.class);
    }

    public String  deleteProductById(String id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return "No product exist with the given id: " + id;
        productRepository.deleteById(id);
        return "Successfully deleted product with id: " + id;
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
