package com.example.crud.service;

import com.example.crud.entity.Categoria;
import com.example.crud.repository.ProductoRepository;
import com.example.crud.repository.CategoriaRepository;
import com.example.crud.dto.CategoriaRequest;
import com.example.crud.dto.CategoriaResponse;
import com.example.crud.mapper.CategoriaMapper;
import com.example.crud.exception.CategoriaDuplicadaException;
import com.example.crud.exception.CategoriaConProductosException;

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
        if (repo.existsByNombreIgnoreCase(request.nombre())) {
            throw new CategoriaDuplicadaException(request.nombre());
        }
        Categoria guardada = repo.save(CategoriaMapper.toEntity(request));
        return CategoriaMapper.toResponse(guardada);
    }

    public Optional<CategoriaResponse> actualizar(Long id, CategoriaRequest request) {
        return repo.findById(id).map(c -> {
            if (repo.existsByNombreIgnoreCaseAndIdNot(request.nombre(), id)){
                throw new CategoriaDuplicadaException(request.nombre());
            }
            c.setNombre(request.nombre());
            return CategoriaMapper.toResponse(repo.save(c));
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
