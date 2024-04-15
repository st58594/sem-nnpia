package cz.upce.nnpia;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValues;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import cz.upce.nnpia.dtos.request.ProductRequest;
import cz.upce.nnpia.model.Role;
import cz.upce.nnpia.model.User;
import cz.upce.nnpia.services.ProductService;
import cz.upce.nnpia.services.RoleService;
import cz.upce.nnpia.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Locale;
import java.util.Set;

@SpringBootApplication
public class NnpiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NnpiaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserService userService, RoleService roleService, ProductService productService){
		return args -> {
//			Faker faker = new Faker(new Locale("cs-CZ"));
			try {
//				roleService.save(Role.builder().role("ADMIN").build());
//				roleService.save(Role.builder().role("PRODUCT-MANAGER").build());
//				roleService.save(Role.builder().role("GUEST").build());
//
//				userService.save(User.builder()
//						.username("admin")
//						.email(faker.internet().emailAddress())
//						.firstName(faker.name().firstName())
//						.lastName(faker.name().lastName())
//						.password("admin")
//						.roles(Set.of(roleService.findByRole("ADMIN")))
//						.build());
//
//				userService.save(User.builder()
//						.username("product-manager")
//						.email(faker.internet().emailAddress())
//						.firstName(faker.name().firstName())
//						.lastName(faker.name().lastName())
//						.password("product-manager")
//						.roles(Set.of(roleService.findByRole("PRODUCT-MANAGER")))
//						.build());
//
//				userService.save(User.builder()
//						.username("guest")
//						.email("guest@guest.cz")
//						.firstName("guest")
//						.lastName("guest")
//						.password("guest")
//						.roles(Set.of(roleService.findByRole("GUEST")))
//						.build());
//
//				for (int i = 0; i < 150; i++) {
//					productService.create(
//							new ProductRequest(
//									faker.commerce().productName(),
//									faker.number().randomDouble(4, 0, 10000)
//							)
//					);
//				}
			} catch (Exception ex){
				System.out.println(ex.getMessage());
			}
		};
	}
}
