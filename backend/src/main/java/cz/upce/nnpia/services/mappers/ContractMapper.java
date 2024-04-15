package cz.upce.nnpia.services.mappers;

import cz.upce.nnpia.model.Contract;
import cz.upce.nnpia.model.State;
import cz.upce.nnpia.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContractMapper {
    public Contract toContract(State state, Double totalPrice, User user){
        return Contract.builder()
                .state(state)
                .totalPrice(totalPrice)
                .user(user)
                .build();
    }
}
