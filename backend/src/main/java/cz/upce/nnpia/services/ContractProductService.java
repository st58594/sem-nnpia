package cz.upce.nnpia.services;

import cz.upce.nnpia.model.ContractProduct;
import cz.upce.nnpia.repositories.ContractProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContractProductService {
    private final ContractProductRepository contractProductRepository;
    public ContractProduct create(ContractProduct contractProduct){
        return contractProductRepository.save(contractProduct);
    }

    public ContractProduct update(ContractProduct contractProduct) {
        return contractProductRepository.save(contractProduct);
    }
}
