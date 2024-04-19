package cz.upce.nnpia.services;

import cz.upce.nnpia.dtos.request.UserRequest;
import cz.upce.nnpia.dtos.response.UserResponse;
import cz.upce.nnpia.exceptions.NotUniqueAttributeException;
import cz.upce.nnpia.exceptions.ResourceNotFoundException;
import cz.upce.nnpia.model.User;
import cz.upce.nnpia.repositories.UserRepository;
import cz.upce.nnpia.services.mappers.RoleMapper;
import cz.upce.nnpia.services.mappers.UserMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

    private UserService underTest;
    private static UserRequest userRequest;
    private static User user;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleMapper roleMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder encoder;

    @BeforeAll
    static void beforeAll() {
        user = User.builder()
                .username("admin")
                .email("admin@admin.cz")
                .password("admin")
                .firstName("admin")
                .lastName("admin")
                .roles(Collections.emptySet())
                .build();
        userRequest = new UserRequest("invalidUsername", "invalidEmail", "admin", "admin", "admin", Collections.emptySet());
    }

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository,roleMapper,userMapper,encoder);
    }

    @Test
    void loadUserByUsername() {
        String username = "admin";
        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(user));

        var expected = underTest.loadUserByUsername(username);
        assertThat(expected).isEqualTo(user);

    }

    @Test
    void loadUserByUsernameThrowWhenUserNameNotExists() {
        String username = "invalidUsername";

        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        assertThatThrownBy(()->underTest.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining(username + " not found");
    }
    @Test
    void findAll() {
        underTest.findAll();
        verify(userRepository).findAll();
    }

    @Test
    void findById() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));

        UserResponse expected = underTest.findById(id);
        verify(userRepository).findById(id);
        assertThat(expected).isEqualTo(user.toDto());

        assertThatThrownBy(()->underTest.findById(2L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void create() {
        when(userMapper.toUser(any())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        underTest.create(userRequest);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        assertEquals(userArgumentCaptor.getValue(), user);
    }

    @Test
    void createWillThrowWhenUserNameIsTaken(){
        given(userRepository.existsByUsername(userRequest.username())).willReturn(true);

        assertThatThrownBy(()->underTest.create(userRequest))
                .isInstanceOf(NotUniqueAttributeException.class)
                .hasMessageContaining("User with this username \""+userRequest.username()+"\" already exist");
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(user);
    }
    @Test
    void createWillThrowWhenEmailIsTaken(){
        given(userRepository.existsByEmail(userRequest.email())).willReturn(true);

        assertThatThrownBy(()->underTest.create(userRequest))
                .isInstanceOf(NotUniqueAttributeException.class)
                .hasMessageContaining("User with this email \""+userRequest.email()+"\" already exist");
        verify(userRepository).existsByUsername(anyString());
        verify(userRepository, never()).save(user);
    }

    @Test
    void update() {
        User updatedUser = user;
        updatedUser.setUsername(userRequest.username());
        updatedUser.setEmail(userRequest.email());
        updatedUser.setPassword(encoder.encode(userRequest.password()));
        updatedUser.setFirstName(userRequest.firstName());
        updatedUser.setFirstName(userRequest.lastName());
        updatedUser.setRoles(roleMapper.toRole(userRequest.roles()));

        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any())).thenReturn(updatedUser);
        underTest.update(id, userRequest);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue()).isEqualTo(updatedUser);
    }

    @Test
    void updateThrowWhenIdNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.update(anyLong(), userRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found");
        verify(userRepository, never()).save(user);
    }
    @Test
    void delete() {
        Long id = 1L;
        given(userRepository.existsById(id)).willReturn(true);

        underTest.delete(id);
        verify(userRepository).deleteById(id);
    }

    @Test
    void deleteThrowWhenIdNotExists(){
        Long id = 1L;
        given(userRepository.existsById(id)).willReturn(false);

        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found");
        verify(userRepository, never()).deleteById(id);
    }
    
}