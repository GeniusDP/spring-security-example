package com.example.securityexample;

import com.example.securityexample.entity.Role;
import com.example.securityexample.entity.User;
import com.example.securityexample.repository.UserRepository;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;

@SpringBootApplication
public class SecurityExampleApplication implements CommandLineRunner {

	@Autowired
	ApplicationContext context;

	@Autowired
	UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SecurityExampleApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("Hello!");
		repository.deleteAll();
		User admin = User.builder()
			.username("admin")
			.roles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER))
			.password("admin")
			.build();
		User user = User.builder()
			.username("user")
			.roles(Set.of(Role.ROLE_USER))
			.password("user")
			.build();
		repository.saveAll(List.of(admin, user));
	}
}
