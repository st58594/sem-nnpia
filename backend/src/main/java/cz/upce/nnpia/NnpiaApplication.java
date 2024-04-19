package cz.upce.nnpia;

import com.github.javafaker.Faker;
import cz.upce.nnpia.dtos.request.*;
import cz.upce.nnpia.model.Role;
import cz.upce.nnpia.model.State;
import cz.upce.nnpia.services.ContractService;
import cz.upce.nnpia.services.ProductService;
import cz.upce.nnpia.services.RoleService;
import cz.upce.nnpia.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
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
    @Profile("!test")
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

                userService.create(new UserRequest(
                                "product-manager",
                                faker.internet().emailAddress(),
                                faker.name().firstName(),
                                faker.name().lastName(),
                                "product-manager",
                                Set.of(new RoleRequest("PRODUCT-MANAGER"))
                        )
                );
                userService.create(new UserRequest(
                                "guest",
                                faker.internet().emailAddress(),
                                faker.name().firstName(),
                                faker.name().lastName(),
                                "guest",
                                Set.of(new RoleRequest("GUEST"))
                        )
                );

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
