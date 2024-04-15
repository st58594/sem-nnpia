package cz.upce.nnpia.services;

import cz.upce.nnpia.dtos.request.ContractProductRequest;
import cz.upce.nnpia.dtos.request.ContractRequest;
import cz.upce.nnpia.dtos.response.ContractResponse;
import cz.upce.nnpia.dtos.response.ProductResponse;
import cz.upce.nnpia.exceptions.ResourceNotFoundException;
import cz.upce.nnpia.model.Contract;
import cz.upce.nnpia.model.ContractProduct;
import cz.upce.nnpia.model.State;
import cz.upce.nnpia.model.User;
import cz.upce.nnpia.repositories.ContractRepository;
import cz.upce.nnpia.services.mappers.ContractMapper;
import cz.upce.nnpia.utils.Auth;
import cz.upce.nnpia.utils.EnumUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;
    private final ProductService productService;
    private final ContractProductService contractProductService;

    public List<ContractResponse> findAll(){
        return contractRepository.findAll()
                .stream()
                .map(Contract::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ContractResponse create(ContractRequest contractRequest){

        //mising id
        //state in request
        State state = EnumUtil.getEnumByString(State.class, contractRequest.state());

        //count total price and check if product exists
        double totalPrice = 0;
        for (ContractProductRequest contractProduct : contractRequest.contractProducts()){
            ProductResponse productResponse = productService.findById(contractProduct.productID());
            totalPrice += productResponse.price() * contractProduct.ordered();
        }

        //user = logged user
        User user = Auth.getLoggedUser();

        //save contract
        Contract savedContract = contractRepository.save(contractMapper.toContract(state, totalPrice, user));

        //save contractProduct
        Set<ContractProduct> savedContractProducts = new HashSet<>();
        for (ContractProductRequest request : contractRequest.contractProducts()){
            ContractProduct contractProduct = new ContractProduct(savedContract.getId(), request.productID(), request.ordered());
            savedContractProducts.add(contractProductService.create(contractProduct));
        }
        savedContract.setContractProducts(savedContractProducts);
        return savedContract.toDto();
    }

    public ContractResponse findById(long id) {
        return contractRepository.findById(id)
                .map(Contract::toDto)
                .orElseThrow(()-> new ResourceNotFoundException("Contract not found"));
    }

    @Transactional
    public ContractResponse update(Long id, ContractRequest contractRequest) {
        Contract contract = contractRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Contract not found"));

        if (!contractRequest.state().isBlank()){
            State state = EnumUtil.getEnumByString(State.class, contractRequest.state());
            contract.setState(state);
        }

        if (contractRequest.contractProducts() != null && !contractRequest.contractProducts().isEmpty()){
            for (var each : contractRequest.contractProducts()){
                ContractProduct savedCP = contractProductService.update(new ContractProduct(id, each.productID(), each.ordered()));
                contract.getContractProducts().removeIf(cp -> savedCP.getId()== cp.getId());
                contract.getContractProducts().add(savedCP);
            }
        }

        contractRepository.save(contract);
        return contract.toDto();
    }

    public void delete(Long id) {
        contractRepository.deleteById(id);
    }
}
