package vn.ntkiet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//import vn.ntkiet.configs.MySiteMeshFilter;

@SpringBootApplication(scanBasePackages = "vn.ntkiet")
@EntityScan(basePackages = "vn.ntkiet.entity")
@EnableJpaRepositories(basePackages = "vn.ntkiet.repository")
public class SpringSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemoApplication.class, args);
	}
	
//	@Bean
//	public FilterRegistrationBean<MySiteMeshFilter> siteMeshFilter() {
//		FilterRegistrationBean<MySiteMeshFilter> filterRegistrationBean = new FilterRegistrationBean<MySiteMeshFilter>();
//        filterRegistrationBean.setFilter(new MySiteMeshFilter()); // adding sitemesh filter??
//        filterRegistrationBean.addUrlPatterns("/*");
//        return filterRegistrationBean;
//	}

}
