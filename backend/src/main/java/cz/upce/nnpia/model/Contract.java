package cz.upce.nnpia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.upce.nnpia.dtos.response.ContractResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contracts")
public class Contract extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated
    @NotNull
    private State state;

    @Column(nullable = false)
    @Min(0)
    @NotNull
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_id"))
    private User user;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<ContractProduct> contractProducts;

    public ContractResponse toDto(){
        return new ContractResponse(
                id,
                state,
                totalPrice,
                user.toDto(),
                contractProducts
                        .stream()
                        .map(ContractProduct::toDto)
                        .collect(Collectors.toSet()),
                created,
                updated
        );
    }



}
