package vn.ntkiet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

//import vn.ntkiet.configs.MySiteMeshFilter;

@SpringBootApplication
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
