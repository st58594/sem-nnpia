package cz.upce.nnpia.repositories;

import cz.upce.nnpia.model.ContractProduct;
import cz.upce.nnpia.model.ContractProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractProductRepository extends JpaRepository<ContractProduct, ContractProductKey> {
}
