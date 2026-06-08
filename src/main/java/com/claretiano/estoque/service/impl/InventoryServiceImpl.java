package com.claretiano.estoque.service.impl;

import com.claretiano.estoque.handler.InventoryNotFoundException;
import com.claretiano.estoque.model.Category;
import com.claretiano.estoque.model.Product;
import com.claretiano.estoque.repository.CategoryRepository;
import com.claretiano.estoque.repository.ProductRepository;
import com.claretiano.estoque.response.InventoryResponseDTO;
import com.claretiano.estoque.service.InventoryService;
import com.claretiano.estoque.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public InventoryServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<InventoryResponseDTO> listarInventario() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public List<InventoryResponseDTO> buscarPorNome(String nome) {
        List<Product> products = productRepository.findAllByNameContainingIgnoreCase(nome);

        if(products.isEmpty()){
            throw new InventoryNotFoundException("Produto '" + nome + "' não encontrado no inventário");
        }

        return products.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public InventoryResponseDTO buscarPorId(Long id) {
        InventoryResponseDTO inventoryDTO = productRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new InventoryNotFoundException("O produto com o id " + id + " não foi encontrado"));
        return inventoryDTO;
    }

    @Override
    public List<InventoryResponseDTO> buscarPorCategoria(String categoria) {
        Category category = categoryRepository.findByNomeNormalizado(StringUtils.normalizar(categoria))
                .orElseThrow(() -> new InventoryNotFoundException("Categoria " + categoria + " não encontrada no inventário"));

        return category.getProducts()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public List<InventoryResponseDTO> estoqueBaixo() {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getQuantity() <= product.getMinStock())
                .map(this::toResponseDTO)
                .toList();
    }

    private InventoryResponseDTO toResponseDTO(Product product){
        return InventoryResponseDTO.builder()
                .id(product.getId())
                .productName(product.getName())
                .quantity(product.getQuantity())
                .minStock(product.getMinStock())
                .categoryName(product.getCategory().getName())
                .build();
    }
}