package org.larin.springmvcjv.contoller;


import com.fasterxml.jackson.annotation.JsonView;
import org.larin.springmvcjv.model.Customer;
import org.larin.springmvcjv.model.CustomerOrder;
import org.larin.springmvcjv.model.view.DetailView;
import org.larin.springmvcjv.model.view.SummaryView;
import org.larin.springmvcjv.service.StoreService;
import org.larin.springmvcjv.service.StoreServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/store")
public class StoreController {

    private final StoreService storeService;


    public StoreController(StoreService storeService) {

        this.storeService = storeService;
    }

    @GetMapping("/customers")
    @JsonView(SummaryView.class)
    public ResponseEntity<List<Customer>> getAllCustomer() {
        List<Customer> customers = storeService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/customers/{id}")
    @JsonView(DetailView.class)
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Customer customer = storeService.getCustomer(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer newCustomer = storeService.createCustomer(customer.getName(), customer.getEmail(),
                customer.getPhoneNumber());
        return ResponseEntity.ok(newCustomer);
    }

    @PutMapping("/customers")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        Customer updatedCustomer = storeService.updateCustomer(customer);
        return ResponseEntity.ok(updatedCustomer);
    }


    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        Customer deletedCustomer = storeService.deleteCustomer(id);
        return ResponseEntity.ok(deletedCustomer);
    }


    @PostMapping("/orders/{customerId}")
    public ResponseEntity<CustomerOrder> createOrder(@PathVariable Long customerId, @RequestBody CustomerOrder order) {
        CustomerOrder customerOrder = storeService.createOrder(customerId, order);
        return ResponseEntity.ok(customerOrder);
    }



}
