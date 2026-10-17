package com.example.crud.controller;

import com.example.crud.dto.ProductoResponse;
import com.example.crud.dto.CategoriaRequest;
import com.example.crud.dto.CategoriaResponse;
import com.example.crud.service.ProductoService;
import com.example.crud.service.CategoriaService;

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

    @PutMapping("/{id}") // PUT a /api/categorias/{id}, Actualiza una categoria completo.
    public ResponseEntity<CategoriaResponse> actualizar(@PathVariable Long id,
            @Valid @RequestBody CategoriaRequest request) { // recibe el id de la ruta y el objeto categoria con los datos a actualizar
        return service.actualizar(id, request) // busca la categoria por id
                .map(ResponseEntity::ok) // devuelve ok si se actualizó correctamente
                .orElse(ResponseEntity.notFound().build()); // devuelve no encontrado si no existe
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
