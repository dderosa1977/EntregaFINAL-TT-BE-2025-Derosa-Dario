package com.tech.speedrun.controller;

import com.tech.speedrun.entity.Product;
import com.tech.speedrun.service.ProductService;
import org.springframework.http.ResponseEntity; // Importante para manejar respuestas HTTP
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            // Intentamos crear el producto
            Product createdProduct = this.productService.createProduct(product);
            // Si funciona, devolvemos OK (200) y el producto
            return ResponseEntity.ok(createdProduct);
        } catch (IllegalArgumentException e) {
            // Si el servicio lanza error (precio negativo, etc), lo atrapamos aqui
            return ResponseEntity.badRequest().body("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            // Cualquier otro error inesperado
            return ResponseEntity.internalServerError().body("Ocurrió un error grave: " + e.getMessage());
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.productService.getProductById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(@RequestParam(required = false, defaultValue = "") String name,
                                        @RequestParam(required = false, defaultValue = "") String category) {
        // En listar generalmente no necesitamos try-catch estricto salvo para base de datos
        return this.productService.findAllProducts(name, category);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> editProductById(@PathVariable Long id, @RequestBody Product dataToEdit) {
        try {
            Product updatedProduct = this.productService.editProductById(id, dataToEdit);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            // Atrapamos errores como ID no encontrado o precio negativo
            return ResponseEntity.badRequest().body("No se pudo editar: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error en el servidor");
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        try {
            Product deleted = this.productService.deleteProductById(id);
            return ResponseEntity.ok(deleted);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No se pudo eliminar el producto con ID " + id);
        }
    }
}