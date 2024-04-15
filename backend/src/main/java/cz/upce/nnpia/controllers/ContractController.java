package cz.upce.nnpia.controllers;

import cz.upce.nnpia.dtos.request.ContractRequest;
import cz.upce.nnpia.dtos.response.ContractResponse;
import cz.upce.nnpia.services.ContractService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/contracts")
public class ContractController {
    private final ContractService contractService;

    @GetMapping
    public List<ContractResponse> getAllContracts(){
        return contractService.findAll();
    }

    @GetMapping("/{id}")
    public ContractResponse getContract(@PathVariable long id){
        return contractService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ContractResponse> createContract(@RequestBody @Valid ContractRequest contractRequest){
        var body = contractService.create(contractRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractResponse> updateContract(@PathVariable Long id, @RequestBody ContractRequest contractRequest){
        var body = contractService.update(id, contractRequest);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id){
        contractService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
