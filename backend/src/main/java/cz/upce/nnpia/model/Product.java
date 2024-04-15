package cz.upce.nnpia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.upce.nnpia.dtos.response.ProductResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Set<ContractProduct> contractProducts = new HashSet<>();

    public ProductResponse toDto(){
        return new ProductResponse(id,name, price);
    }

}
