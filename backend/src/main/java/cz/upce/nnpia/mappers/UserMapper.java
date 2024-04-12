package cz.upce.nnpia.mappers;

import cz.upce.nnpia.dtos.request.UserRequest;
import cz.upce.nnpia.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserMapper {
    private final RoleMapper roleMapper;
    private final PasswordEncoder encoder;

    public User toUser(UserRequest userRequest){
        return User.builder()
                .username(userRequest.username())
                .email(userRequest.email())
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .password(encoder.encode(userRequest.password()))
                .roles(roleMapper.toRole(userRequest.roles()))
                .build();
    }
}
