package vn.ntkiet.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.validation.constraints.AssertFalse.List;
import vn.ntkiet.repository.UserInfoRepository;
import vn.ntkiet.services.UserInfoService;

import java.util.ArrayList;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
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
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) -> auth
                		.requestMatchers("/hello").permitAll() // Endpoint "/hello" luôn cho phép qua
                		.requestMatchers("/").hasAnyAuthority("USER", "ADMIN", "EDITOR", "CREATOR")
                		.requestMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR")
                		.requestMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
                		.requestMatchers("/delete/**").hasAnyAuthority("ADMIN")                	
                		.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                		.requestMatchers("/api/**").permitAll()
//                        .requestMatchers("/customer/all").hasRole("ADMIN") // Yêu cầu ROLE_ADMIN cho /customer/all
//                        .requestMatchers("/customer/{id}").hasRole("USER") // Yêu cầu ROLE_USER cho /customer/{id}
                        .anyRequest().authenticated() // Yêu cầu xác thực cho các endpoint còn lại
                        )
//              .formLogin(Customizer.withDefaults())
//              .httpBasic(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .formLogin(login -> login.loginPage("/login").permitAll())
                .logout(logout -> logout.permitAll())
                .exceptionHandling(handling -> handling.accessDeniedPage("/403"))
                .build();
    }
	
	 @Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		final java.util.List<GlobalAuthenticationConfigurerAdapter> configures = new ArrayList<>();
		configures.add(new GlobalAuthenticationConfigurerAdapter() {
			@Override
			public void configure(AuthenticationManagerBuilder auth) throws Exception {
				
			}
		});
		return authConfig.getAuthenticationManager();
	}
	
}
