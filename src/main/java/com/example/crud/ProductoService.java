package com.example.crud;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
//Le dice a srping esta clase contiene la lógica del negocio y la deja lista para inyectarla donde haga falta, al igual que en el repositorio
public class ProductoService {

    private final ProductoRepository repo;
    //inyeccion de dependencias  privado. Ahora es el servicio quien maneja la interaccion con la base de datos a traves del repositorio y no el controlador. El repo es ahora una dependencia interna del servicio. 

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }
    //constructor que recibe el repositorio como parametro  y se lo asigna a la variable privada repo

    public List<Producto> listar() {
        return repo.findAll();
    }
    //busca todos los productos
    public Optional<Producto> obtener(Long id) {
        return repo.findById(id);
    }
    //crea un producto
    public Producto crear(Producto producto) {
        if (repo.existsByNombreIgnoreCase(producto.getNombre())) {
            throw new NombreDuplicadoException(producto.getNombre());
        }
        producto.setId(null);   //le ponemos null al id porque el id siempre lo asigna la base de datos. Es la primera regla del negocio. Sin ella, si alguien envia un id en un POST, save sobreescribe en lugar de crear uno nuevo
        return repo.save(producto);
    }
    //actualiza un producto por id
    public Optional<Producto> actualizar(Long id, Producto datos) {
        return repo.findById(id).map(p -> {
            if (repo.existsByNombreIgnoreCaseAndIdNot(datos.getNombre(), id)) {
                throw new NombreDuplicadoException(datos.getNombre());
            }
            p.setNombre(datos.getNombre());
            p.setPrecio(datos.getPrecio());
            return repo.save(p);
        });
    }
    //elimina un producto por id
    public boolean eliminar(Long id) {
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}