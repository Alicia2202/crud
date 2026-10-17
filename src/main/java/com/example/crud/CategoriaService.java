package com.example.crud;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository repo;
    private final ProductoRepository productoRepo;

    public CategoriaService(CategoriaRepository repo, ProductoRepository productoRepo) {
        this.repo = repo;
        this.productoRepo = productoRepo;
    }

    public List<CategoriaResponse> listar() {
        return repo.findAll().stream()
                .map(CategoriaMapper::toResponse)
                .toList();
    }

    public Optional<CategoriaResponse> obtener(Long id) {
        return repo.findById(id).map(CategoriaMapper::toResponse);
    }

    public CategoriaResponse crear(CategoriaRequest request) {
        Categoria guardada = repo.save(CategoriaMapper.toEntity(request));
        return CategoriaMapper.toResponse(guardada);
    }

    public Optional<CategoriaResponse> actualizar(Long id, CategoriaRequest request) {
        return repo.findById(id).map(entity -> {
            entity.setNombre(request.nombre());
            return CategoriaMapper.toResponse(repo.save(entity));
        });
    }

    public boolean eliminar(Long id) {
        if (!repo.existsById(id)) {
            return false;
        }
        if (productoRepo.existsByCategoriaId(id)) {
            throw new CategoriaConProductosException(id); //si boorad una categoria con productos da error 409, por lo que hay que borrar antes los productos asociados o poner esta categoria como nula en los productos
        }
        repo.deleteById(id);
        return true;
    }
}