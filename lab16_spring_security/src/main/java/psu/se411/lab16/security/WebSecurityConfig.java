package psu.se411.lab16.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	/**
	 * Access rules (checked from top to bottom):
	 *  - "/"                 : everybody, no login needed
	 *  - "/dashboard/**"      : users with the role USER
	 *  - "/admindashboard/**" : users with the role ADMIN
	 *  - everything else (the /api/v1/courses CRUD API): any logged-in user
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/").permitAll()
				.requestMatchers("/dashboard/**").hasRole("USER")
				.requestMatchers("/admindashboard/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form.permitAll())
			.logout((logout) -> logout.permitAll());

		return http.build();
	}

	/** Two in-memory users: a normal user and an admin. Passwords are stored BCrypt-encoded. */
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		UserDetails user = User.builder()
				.username("usera")
				.password(passwordEncoder.encode("pwd123"))
				.roles("USER")
				.build();

		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder.encode("admin123"))
				.roles("ADMIN")
				.build();

		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
