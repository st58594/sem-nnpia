package cz.upce.nnpia.controllers;

import cz.upce.nnpia.dtos.request.ProductRequest;
import cz.upce.nnpia.dtos.response.ProductResponse;
import cz.upce.nnpia.model.Product;
import cz.upce.nnpia.services.ProductService;
import cz.upce.nnpia.services.specifications.ProductSpecification;
import cz.upce.nnpia.utils.SortOrderUtil;
import cz.upce.nnpia.values.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public List<ProductResponse> getAllProducts(
            @RequestParam(required = false, defaultValue = "0") @Min(0) @Max(Integer.MAX_VALUE) Integer page,
            @RequestParam(required = false, defaultValue = Constants.PAGE_SIZE_STR) @Min(1) @Max(Constants.MAX_PAGE_SIZE) Integer pageSize,
            @RequestParam(required = false, defaultValue = Constants.ORDER_BY_CREATED_DESC) String sort,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double fromPrice,
            @RequestParam(required = false) Double toPrice
    ){
        List<Sort.Order> orders = SortOrderUtil.stringPairsToOrders(new String[] {sort});
        Specification<Product> filter = ProductSpecification.filterBy(name, fromPrice, toPrice);
        return productService.findAll(filter, PageRequest.of(page, pageSize, Sort.by(orders)));
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id){
        return productService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productRequest){
        var body = productService.create(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest){
        var body = productService.update(id, productRequest);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
