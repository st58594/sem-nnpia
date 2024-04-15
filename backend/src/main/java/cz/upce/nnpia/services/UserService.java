package cz.upce.nnpia.services;

import cz.upce.nnpia.dtos.request.UserRequest;
import cz.upce.nnpia.dtos.response.UserResponse;
import cz.upce.nnpia.exceptions.NotUniqueAttributeException;
import cz.upce.nnpia.exceptions.ResourceNotFoundException;
import cz.upce.nnpia.services.mappers.RoleMapper;
import cz.upce.nnpia.services.mappers.UserMapper;
import cz.upce.nnpia.model.User;
import cz.upce.nnpia.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
    }

    @Transactional
    public void save(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(User::toDto)
                .collect(Collectors.toList());
    }

    public UserResponse findById(Long id) {
        return userRepository.findById(id)
                .map(User::toDto)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
    }

    public UserResponse create(UserRequest userRequest) {
        checkUsername(userRequest.username());
        checkEmail(userRequest.email());
        return userRepository.save(userMapper.toUser(userRequest)).toDto();
    }

    public UserResponse update(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found."));

        if (!userRequest.username().isBlank()){
            checkUsername(userRequest.username());
            user.setUsername(userRequest.username());
        }
        if (!userRequest.email().isBlank()){
            checkEmail(userRequest.email());
            user.setEmail(userRequest.email());
        }
        if (userRequest.password() != null)
            user.setPassword(encoder.encode(userRequest.password()));
        if (!userRequest.firstName().isBlank())
            user.setFirstName(userRequest.firstName());
        if (!userRequest.lastName().isBlank())
            user.setLastName(userRequest.lastName());
        if (!userRequest.roles().isEmpty())
            user.setRoles(roleMapper.toRole(userRequest.roles()));

        return userRepository.save(user).toDto();
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    private void checkUsername(String username){
        if (userRepository.existsByUsername(username))
            throw new NotUniqueAttributeException("User with this username already exist");
    }

    private void checkEmail(String email){
        if (userRepository.existsByEmail(email))
            throw new NotUniqueAttributeException("User with this email already exist");
    }
}
