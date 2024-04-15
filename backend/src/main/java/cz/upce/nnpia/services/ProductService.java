package cz.upce.nnpia.services;

import cz.upce.nnpia.dtos.request.ProductRequest;
import cz.upce.nnpia.dtos.response.ProductResponse;
import cz.upce.nnpia.exceptions.ResourceNotFoundException;
import cz.upce.nnpia.mappers.ProductMapper;
import cz.upce.nnpia.model.Product;
import cz.upce.nnpia.repositories.ProductRepository;
import cz.upce.nnpia.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserRepository userRepository;



    public List<ProductResponse> findAll(Specification<Product> filter, PageRequest pageRequest) {
        return productRepository.findAll(filter, pageRequest)
                .stream()
                .map(Product::toDto)
                .collect(Collectors.toList());
    }
    public ProductResponse findById(Long id) {
        return productRepository.findById(id)
                .map(Product::toDto)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
    }
    public ProductResponse create(ProductRequest productRequest) {
        return productRepository.save(productMapper.toProduct(productRequest)).toDto();
    }

    public ProductResponse update(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found"));

        if (!productRequest.name().isBlank())
            product.setName(productRequest.name());

        if (productRequest.price() != null)
            product.setPrice(productRequest.price());

        return productRepository.save(product).toDto();
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id))
            throw new ResourceNotFoundException("Product not found");
        productRepository.deleteById(id);
    }

}
