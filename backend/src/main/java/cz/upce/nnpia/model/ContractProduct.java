package cz.upce.nnpia.model;

import cz.upce.nnpia.dtos.response.ContractProductResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contract_products")
public class ContractProduct {
    @EmbeddedId
    private ContractProductKey id = new ContractProductKey();

    @Column(nullable = false)
    @Min(0)
    @Max(Integer.MAX_VALUE)
    @NotNull
    private Integer ordered;

    @ManyToOne
    @MapsId("contractId")
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    public ContractProduct(Long contractId, Long productId, int ordered) {
        id = new ContractProductKey(contractId, productId);
        this.ordered = ordered;
        this.contract = Contract.builder().id(contractId).build();
        this.product = Product.builder().id(productId).build();
    }

    public ContractProductResponse toDto(){
        return new ContractProductResponse(product.toDto(), ordered);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractProduct that = (ContractProduct) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
