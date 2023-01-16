package com.authenticationusingjwt;

import com.authenticationusingjwt.models.Role;
import com.authenticationusingjwt.models.User;
import com.authenticationusingjwt.models.UserRole;
import com.authenticationusingjwt.services.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@SecurityScheme(name = "Bearer", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT")
@OpenAPIDefinition(info = @Info(title = "User API", version = "1.0", description = "User Details"))
@Configuration
public class AuthenticationUsingJwtApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationUsingJwtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Application Started Successfully!");
	/* Creating User using CommandLineRunner
		User user = new User();
		user.setUsername("mutaheraffan");
		user.setPassword("abcd1234");

		Role role1 = new Role();
		role1.setRole_id(45L);
		role1.setRoleName("ADMIN");

		Set<UserRole> userRoleSet = new HashSet<>();
		UserRole userRole = new UserRole();
		userRole.setRole(role1);
		userRole.setUser(user);

		userRoleSet.add(userRole);

		User user1 = this.userService.createUser(user, userRoleSet);
		System.out.println(user1.getUsername());

	 */
	}
}
