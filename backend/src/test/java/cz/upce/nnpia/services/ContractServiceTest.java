package cz.upce.nnpia.services;

import com.github.javafaker.Faker;
import cz.upce.nnpia.dtos.request.*;
import cz.upce.nnpia.dtos.response.ProductResponse;
import cz.upce.nnpia.model.Role;
import cz.upce.nnpia.model.State;
import cz.upce.nnpia.repositories.ContractProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;
import java.util.stream.Collectors;

@ActiveProfiles("test")
@SpringBootTest
class ContractServiceTest {

    @Autowired
    private ContractService underTest;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ContractProductRepository contractProductRepository;


    @BeforeEach
    void setUp() {
        final int MAX_PRODUCTS = 10;
        Faker faker = new Faker();

        roleService.save(Role.builder().role("ADMIN").build());
        roleService.save(Role.builder().role("PRODUCT-MANAGER").build());
        roleService.save(Role.builder().role("GUEST").build());


        userService.create(new UserRequest(
                        "admin",
                        faker.internet().emailAddress(),
                        faker.name().firstName(),
                        faker.name().lastName(),
                        "admin",
                        Set.of(new RoleRequest("ADMIN"))
                )
        );

        //fake login
        UserDetails userDetails = userService.loadUserByUsername("admin");
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        for (int i = 0; i < MAX_PRODUCTS; i++) {
            productService.create(
                    new ProductRequest(
                            faker.commerce().productName(),
                            faker.number().randomDouble(4, 0, 10000),
                            faker.number().numberBetween(-10, 100)
                    )
            );
        }
    }
    @Test
    void create() {
        Page<ProductResponse> productResponseList = productService.findAll(null, PageRequest.of(0,2));
        Set<ContractProductRequest> contractProductRequests = productResponseList
                .stream()
                .map(productResponse -> new ContractProductRequest(productResponse.id(), 1))
                .collect(Collectors.toSet());
        ContractRequest contractRequest = new ContractRequest(State.CREATED.getName(), contractProductRequests);

        Assertions.assertNotNull(underTest.create(contractRequest));
        Assertions.assertEquals((long) contractProductRepository.findAll().size(), 2);

    }
}