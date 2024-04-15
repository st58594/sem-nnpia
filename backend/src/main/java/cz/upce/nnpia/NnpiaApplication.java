package cz.upce.nnpia;

import com.github.javafaker.Faker;
import cz.upce.nnpia.dtos.request.ContractProductRequest;
import cz.upce.nnpia.dtos.request.ContractRequest;
import cz.upce.nnpia.dtos.request.ProductRequest;
import cz.upce.nnpia.model.Role;
import cz.upce.nnpia.model.State;
import cz.upce.nnpia.model.User;
import cz.upce.nnpia.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class NnpiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(NnpiaApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserService userService, RoleService roleService, ProductService productService, ContractService contractService) {
        return args -> {
            Faker faker = new Faker();
            final int MAX_PRODUCTS = 150;
            final int MAX_CONTRACT = 20;
            final int MAX_CONTRACT_PRODUCTS = 10;
            final int MAX_ORDERED = 10;
            try {
                roleService.save(Role.builder().role("ADMIN").build());
                roleService.save(Role.builder().role("PRODUCT-MANAGER").build());
                roleService.save(Role.builder().role("GUEST").build());

                userService.save(User.builder()
                        .username("admin")
                        .email(faker.internet().emailAddress())
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .password("admin")
                        .roles(Set.of(roleService.findByRole("ADMIN")))
                        .build());

                UserDetails userDetails = userService.loadUserByUsername("admin");
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                userService.save(User.builder()
                        .username("product-manager")
                        .email(faker.internet().emailAddress())
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .password("product-manager")
                        .roles(Set.of(roleService.findByRole("PRODUCT-MANAGER")))
                        .build());

                userService.save(User.builder()
                        .username("guest")
                        .email("guest@guest.cz")
                        .firstName("guest")
                        .lastName("guest")
                        .password("guest")
                        .roles(Set.of(roleService.findByRole("GUEST")))
                        .build());

                for (int i = 0; i < MAX_PRODUCTS; i++) {
                    productService.create(
                            new ProductRequest(
                                    faker.commerce().productName(),
                                    faker.number().randomDouble(4, 0, 10000),
                                    faker.number().numberBetween(-10, 100)
                            )
                    );
                }

                for (int i = 0; i < MAX_CONTRACT; i++) {
                    Set<ContractProductRequest> contractProductRequests = new HashSet<>();
                    for (int j = 0; j < faker.number().numberBetween(1, MAX_CONTRACT_PRODUCTS); j++) {
                        contractProductRequests.add(
                                new ContractProductRequest(
                                        faker.number().numberBetween(1L, MAX_PRODUCTS),
                                        faker.number().numberBetween(1, MAX_ORDERED)
                                )
                        );
                    }
                    contractService.create(
                            new ContractRequest(
                                    faker.options().option(State.class).getName(),
                                    contractProductRequests
                            )
                    );
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
        };
    }
}
