package com.kientran.product_service.service;

import com.kientran.product_service.dto.ProductDto;
import com.kientran.product_service.dto.TotalProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto, Integer categoryId);

    ProductDto updateProduct(ProductDto productDto, Integer productId);

    void deleteProduct(Integer productId);

    ProductDto getProductById(Integer productId);

    public List<ProductDto> getAllProducts();

    ProductDto applyDiscount(Integer productId, Integer discountId);

    public ProductDto removeDiscount(Integer productId);

    public List<ProductDto> getProductsByCategory(Integer categoryId);

    TotalProductDto getTotalProductInStore();

    public List<ProductDto> getProductsHaveDiscount();

}
