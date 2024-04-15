package cz.upce.nnpia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.upce.nnpia.dtos.response.ProductResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @NotEmpty
    private String name;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    private double price;

    @Column(nullable = false)
    @NotNull
    @Min(Integer.MIN_VALUE)
    @Max(Integer.MAX_VALUE)
    private int inStock;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Set<ContractProduct> contractProducts = new HashSet<>();

    public ProductResponse toDto(){
        return new ProductResponse(id,name, price, inStock);
    }

}
