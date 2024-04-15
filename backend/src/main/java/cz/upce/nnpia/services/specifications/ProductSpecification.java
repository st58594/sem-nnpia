package cz.upce.nnpia.services.specifications;

import cz.upce.nnpia.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> filterBy(String name, Double fromPrice, Double toPrice){
        return Specification.where(containName(name))
                .and(priceGT(fromPrice))
                .and(priceLT(toPrice));
    }

    public static Specification<Product> containName(String name) {
        return (root, query, criteriaBuilder) -> name == null || name.isEmpty()
                ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> priceGT(Double from) {
        return (root, query, criteriaBuilder) -> from == null
                ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), from);
    }

    public static Specification<Product> priceLT(Double to) {
        return (root, query, criteriaBuilder) -> to == null
                ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("price"), to);
    }
}
