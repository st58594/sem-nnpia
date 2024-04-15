package cz.upce.nnpia.mappers;

import cz.upce.nnpia.dtos.request.ProductRequest;
import cz.upce.nnpia.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public Product toProduct(ProductRequest productRequest){
        return Product.builder()
                .name(productRequest.name())
                .price(productRequest.price())
                .inStock(productRequest.inStock())
                .build();
    }
}
