package com.example.crud;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid; //para validar los datos del producto
//aqui están los endpoints.

@RestController // esta clase atiende a peticiones HTTp y lo que devuelve sus métodos se
                // convierte en JSON automáticamente
@RequestMapping("/api/productos") // todas las rutas de esta clase empiezan por /api/productos
public class ProductoController {

    private final ProductoService service; // variable privada llamada service que apunta al service y no se cambia
                                           // (final), es como que te dan esta herramienta y no se va a cambiar.

    public ProductoController(ProductoService service) { // constructor de objetos que recibe el service. Esto es una
                                                         // inyeccion de dependencias, clave de Spring. No escribo new
                                                         // ProductoService(). Spring ve que el constructor necesita un
                                                         // service lo crea y me lo pasa.
        this.service = service;
    }

    @GetMapping // GET a /api/productos , devuelve la lista completa en JSON
    public List<ProductoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}") // GET a /api/productos/1, devuelve un solo producto
    public ResponseEntity<ProductoResponse> obtener(@PathVariable Long id) { // devuelve una respuesta HTTP usando la anotacion
                                                                     // de la direccion web para capturar el id de la
                                                                     // url.
        return service.obtener(id) // Busca el id
                .map(ResponseEntity::ok) // Si lo encuentra devuelve ok
                .orElse(ResponseEntity.notFound().build()); // Si no lo encuentra devuelve no encontrado
    }

    @PostMapping // POST a /api/productos, Crea un producto
    public ProductoResponse crear(@Valid @RequestBody ProductoRequest request) { // Spring recibe el JSON y lo convierte en un objeto
                                                            // Producto
        return service.crear(request); // Lo guarda en la base de datos, con su id asignado.
    }

    @PutMapping("/{id}") // PUT a /api/productos/{id}, Actualiza un producto completo.
    public ResponseEntity<ProductoResponse> actualizar(@PathVariable Long id,
            @Valid @RequestBody ProductoRequest request) { // recibe el id de la ruta y el objeto producto con los datos a actualizar
        return service.actualizar(id, request) // busca el producto por id
                .map(ResponseEntity::ok) // devuelve ok si se actualizó correctamente
                .orElse(ResponseEntity.notFound().build()); // devuelve no encontrado si no existe
    }

    @DeleteMapping("/{id}") // DELETE a /api/productos/{id}, elimina un producto
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!service.eliminar(id)) {
            return ResponseEntity.notFound().build(); // si no existe devuelve no encontrado
        }
        return ResponseEntity.noContent().build(); // 204 no content, respuesta exitosa sin contenido.
    }
}