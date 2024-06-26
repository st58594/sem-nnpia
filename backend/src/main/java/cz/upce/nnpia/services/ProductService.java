package cz.upce.nnpia.services;

import cz.upce.nnpia.dtos.request.ProductRequest;
import cz.upce.nnpia.dtos.response.ProductResponse;
import cz.upce.nnpia.exceptions.ResourceNotFoundException;
import cz.upce.nnpia.model.Product;
import cz.upce.nnpia.repositories.ProductRepository;
import cz.upce.nnpia.services.mappers.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Page<ProductResponse> findAll(Specification<Product> filter, PageRequest pageRequest) {
        return productRepository.findAll(filter, pageRequest)
                .map(Product::toDto);
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

        if (productRequest.inStock() != null)
            product.setInStock(productRequest.inStock());

        return productRepository.save(product).toDto();
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id))
            throw new ResourceNotFoundException("Product not found");
        productRepository.deleteById(id);
    }

}
