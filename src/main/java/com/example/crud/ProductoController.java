package com.example.crud;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
//aqui están los endpoints.

@RestController //esta clase atiende a peticiones HTTp y lo que devuelve sus métodos se convierte en JSON automáticamente
@RequestMapping("/api/productos") //todas las rutas de esta clase empiezan por /api/productos
public class ProductoController {

    private final ProductoRepository repo;  //variable privada llamada repo que apunta al repository y no se cambia (final), es como que te dan esta herramienta y no se va a cambiar.

    public ProductoController(ProductoRepository repo) { //constructor de objetos que recibe el repository. Esto es una inyeccion de dependencias, clave de Spring. No escribo new ProductoRepository(). Spring ve que el constructor necesita un repository lo crea y me lo pasa.
        this.repo = repo;
    }

    @GetMapping //GET a /api/productos , devuelve la lista completa en JSON 
    public List<Producto> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}") // GET a /api/productos/1, devuelve un solo producto
    public ResponseEntity<Producto> obtener(@PathVariable Long id) { //devuelve una respuesta  HTTP usando la anotacion de la direccion web para capturar el id de la url.
        return repo.findById(id) //Busca el id
                .map(ResponseEntity::ok) //Si lo encuentra devuelve ok
                .orElse(ResponseEntity.notFound().build()); //Si no lo encuentra devuelve no encontrado
    }

    @PostMapping //POST a /api/productos, Crea un producto 
    public Producto crear(@RequestBody Producto producto) { //Spring recibe el JSON y lo convierte en un objeto Producto
        return repo.save(producto); //Lo guarda en la base de datos, con su id asignado.
    }

    @PutMapping("/{id}") //PUT a /api/productos/{id}, Actualiza un producto completo.
    public ResponseEntity<Producto> actualizar(@PathVariable Long id,
                                               @RequestBody Producto datos) { //recibe el id de la ruta y el objeto producto con los datos a actualizar
        return repo.findById(id).map(p -> { // busca el producto por id
            p.setNombre(datos.getNombre()); //actualiza el nombre
            p.setPrecio(datos.getPrecio()); //actualiza el precio
            return ResponseEntity.ok(repo.save(p)); //devuelve ok si se actualizó correctamente
        }).orElse(ResponseEntity.notFound().build()); //devuelve no encontrado si no existe
    }

    @DeleteMapping("/{id}") //DELETE a /api/productos/{id}, elimina un producto
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build(); //si no existe devuelve no encontrado
        }
        repo.deleteById(id); //lo elimina
        return ResponseEntity.noContent().build(); //204 no content, respuesta exitosa sin contenido.
    }
}