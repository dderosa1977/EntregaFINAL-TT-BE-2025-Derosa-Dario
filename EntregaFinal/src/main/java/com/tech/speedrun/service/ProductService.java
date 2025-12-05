package com.tech.speedrun.service;

import com.tech.speedrun.entity.Product;
import com.tech.speedrun.repository.ProductRepository;
import com.tech.speedrun.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final StringUtils stringUtils;

    public ProductService(ProductRepository productRepository, StringUtils stringUtils) {
        this.productRepository = productRepository;
        this.stringUtils = stringUtils;
    }

    public Product createProduct(Product product) {
        // VALIDACIÓN: Usamos try-catch simulado o validaciones directas
        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo ni nulo.");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo ni nulo.");
        }

        log.info("Producto ingresado correctamente: {}", product);
        return this.productRepository.save(product);
    }

    public Product getProductById(Long id) {
        Optional<Product> productOptional = this.productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("Error: No se encontró el producto con ID: " + id);
        }
        return productOptional.get();
    }

    public List<Product> findAllProducts(String name, String category) {
        boolean hasName = !stringUtils.isEmpty(name);
        boolean hasCategory = !stringUtils.isEmpty(category);

        if (hasName && hasCategory) {
            return this.productRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name, category);
        } else if (hasName) {
            return this.productRepository.findByNameContainingIgnoreCase(name);
        } else if (hasCategory) {
            return this.productRepository.findByCategoryContainingIgnoreCase(category);
        }

        return this.productRepository.findAll();
    }

    // AQUI ESTA LA MAGIA DE LA EDICION TOTAL
    public Product editProductById(Long id, Product dataToEdit) {
        // Primero buscamos si existe (si no, getProductById lanza la excepción)
        Product product = this.getProductById(id);

        // Validamos datos numéricos antes de asignar
        if (dataToEdit.getPrice() != null && dataToEdit.getPrice() < 0) {
            throw new IllegalArgumentException("No puedes actualizar a un precio negativo.");
        }

        // Actualizamos CUALQUIER campo que no venga nulo
        if (!stringUtils.isEmpty(dataToEdit.getName())) {
            product.setName(dataToEdit.getName());
        }
        if (!stringUtils.isEmpty(dataToEdit.getDescription())) {
            product.setDescription(dataToEdit.getDescription());
        }
        if (dataToEdit.getPrice() != null) {
            product.setPrice(dataToEdit.getPrice());
        }
        if (!stringUtils.isEmpty(dataToEdit.getCategory())) {
            product.setCategory(dataToEdit.getCategory());
        }
        if (dataToEdit.getStock() != null) {
            product.setStock(dataToEdit.getStock());
        }

        return this.productRepository.save(product);
    }

    public Product deleteProductById(Long id) {
        Product product = this.getProductById(id);
        this.productRepository.delete(product);
        return product;
    }
}