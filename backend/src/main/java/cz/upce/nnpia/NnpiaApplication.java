package cz.upce.nnpia;

import cz.upce.nnpia.model.Role;
import cz.upce.nnpia.model.User;
import cz.upce.nnpia.services.RoleService;
import cz.upce.nnpia.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class NnpiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NnpiaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserService userService, RoleService roleService){
		return args -> {
			try {
				roleService.save(Role.builder().role("ADMIN").build());
				roleService.save(Role.builder().role("GUEST").build());

				userService.save(User.builder()
						.username("admin")
						.email("admin@admin.cz")
						.firstName("admin")
						.lastName("admin")
						.password("admin")
						.roles(Set.of(roleService.findByRole("ADMIN")))
						.build());

				userService.save(User.builder()
						.username("guest")
						.email("guest@guest.cz")
						.firstName("guest")
						.lastName("guest")
						.password("guest")
						.roles(Set.of(roleService.findByRole("GUEST")))
						.build());
			} catch (Exception ex){
				System.out.println(ex.getMessage());
			}
		};
	}
}
