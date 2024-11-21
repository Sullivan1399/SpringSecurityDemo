package vn.ntkiet.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import vn.ntkiet.models.Customer;

@RestController
public class CustomerController {

    final private List<Customer> customers = List.of(
            Customer.builder().id("1").name("customer 1").email("c1@gmail.com").build(),
            Customer.builder().id("2").name("customer 2").email("c2@gmail.com").build());

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello is exception");
    }

    @GetMapping("/customer/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Customer>> getCustomerList(){
    	List<Customer> customers = this.customers;
    	return ResponseEntity.ok(customers);
    }
    
    @GetMapping("/customer/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Customer> getcustomerById(@PathVariable("id") String id) {
        Customer customer = this.customers.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null); // Trả về null nếu không tìm thấy
        
        if (customer == null) {
            return ResponseEntity.notFound().build(); // HTTP 404 nếu không tìm thấy customer
        }

        return ResponseEntity.ok(customer);
    }
}
