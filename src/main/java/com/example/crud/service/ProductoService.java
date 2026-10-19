package com.example.crud.service;

import com.example.crud.entity.Producto;
import com.example.crud.entity.Categoria;
import com.example.crud.repository.ProductoRepository;
import com.example.crud.repository.CategoriaRepository;
import com.example.crud.dto.ProductoRequest;
import com.example.crud.dto.ProductoResponse;
import com.example.crud.mapper.ProductoMapper;
import com.example.crud.exception.NombreDuplicadoException;
import com.example.crud.exception.CategoriaNoEncontradaException;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
//Le dice a srping esta clase contiene la lógica del negocio y la deja lista para inyectarla donde haga falta, al igual que en el repositorio
public class ProductoService {

    private final ProductoRepository repo;
    //inyeccion de dependencias  privado. Ahora es el servicio quien maneja la interaccion con la base de datos a traves del repositorio y no el controlador. El repo es ahora una dependencia interna del servicio. 
    private final CategoriaRepository categoriaRepo;
    
    public ProductoService(ProductoRepository repo, CategoriaRepository categoriaRepo) {
        this.repo = repo;
        this.categoriaRepo = categoriaRepo;
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
    public List<ProductoResponse> listarPorCategoria(Long categoriaId) {   // ← NUEVO
        if (!categoriaRepo.existsById(categoriaId)) {
            throw new CategoriaNoEncontradaException(categoriaId);
        }
        return repo.findByCategoriaId(categoriaId).stream()
                .map(ProductoMapper::toResponse)
                .toList();
    }
    //crea un producto
    public ProductoResponse crear(ProductoRequest request) {
       if (repo.existsByNombreIgnoreCase(request.nombre())) {
            throw new NombreDuplicadoException(request.nombre());
        }
        Categoria categoria = buscarCategoria(request.categoriaId());
        Producto guardado = repo.save(ProductoMapper.toEntity(request, categoria));
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
            p.setCategoria(buscarCategoria(request.categoriaId()));
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

    private Categoria buscarCategoria(Long id) { //Metodo privado que busca una categoria por id y si no la encuentra lanza una excepcion
        return categoriaRepo.findById(id)
        .orElseThrow(() -> new CategoriaNoEncontradaException(id));//si no lo encuentra lanza un error con 404
    }
}
