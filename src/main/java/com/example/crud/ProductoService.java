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

    public List<ProductoResponse> listar() {
        
        return repo.findAll().stream()
        .map(ProductoMapper::toResponse)
        .toList();
    }
    //busca todos los productos
    public Optional<ProductoResponse> obtener(Long id) {
        return repo.findById(id).map(ProductoMapper::toResponse);
    }
    //crea un producto
    public ProductoResponse crear(ProductoRequest request) {
       if (repo.existsByNombreIgnoreCase(request.nombre())) {
            throw new NombreDuplicadoException(request.nombre());
        }
        Producto guardado = repo.save(ProductoMapper.toEntity(request));
        return ProductoMapper.toResponse(guardado);
    }
    //actualiza un producto por id
    public Optional<ProductoResponse> actualizar(Long id, ProductoRequest request) {
        return repo.findById(id).map(p -> {
            if (repo.existsByNombreIgnoreCaseAndIdNot(request.nombre(), id)) {
                throw new NombreDuplicadoException(request.nombre());
            }
            p.setNombre(request.nombre());
            p.setPrecio(request.precio());
            return ProductoMapper.toResponse(repo.save(p));
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