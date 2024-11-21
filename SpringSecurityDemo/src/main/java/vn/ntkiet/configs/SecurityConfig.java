package vn.ntkiet.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import vn.ntkiet.repository.UserInfoRepository;
import vn.ntkiet.services.UserInfoService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	UserInfoRepository repository;
	
	@Bean
	// authentication
	public UserDetailsService userDetailsService() {
//	    UserDetails admin = User.withUsername("admin")
//	            .password(encoder.encode("password"))
//	            .roles("ADMIN")
//	            .build();
//	    UserDetails user = User.withUsername("ntk")
//	            .password(encoder.encode("123"))
//	            .roles("USER")
//	            .build();
//	    return new InMemoryUserDetailsManager(admin, user);
		return new UserInfoService(repository);
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                		.requestMatchers("/hello").permitAll() // Endpoint "/hello" luôn cho phép qua
                		.requestMatchers("/user/new").permitAll()
                        .requestMatchers("/customer/all").hasRole("ADMIN") // Yêu cầu ROLE_ADMIN cho /customer/all
                        .requestMatchers("/customer/{id}").hasRole("USER") // Yêu cầu ROLE_USER cho /customer/{id}
//                        .anyRequest().authenticated() // Yêu cầu xác thực cho các endpoint còn lại
                        )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
	
	 @Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}
