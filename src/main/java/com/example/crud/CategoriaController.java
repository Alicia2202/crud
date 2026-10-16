package com.example.crud;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService service;
    private final ProductoService productoService;

    public CategoriaController(CategoriaService service, ProductoService productoService) {
        this.service = service;
        this.productoService = productoService;
    }

    @GetMapping
    public List<CategoriaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> obtener(@PathVariable Long id) {
        return service.obtener(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/productos")
    public List<ProductoResponse> productos(@PathVariable Long id) {
        return productoService.listarPorCategoria(id);
    }

    @PostMapping
    public CategoriaResponse crear(@Valid @RequestBody CategoriaRequest request) {
        return service.crear(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}