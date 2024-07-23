package org.larin.springmvcjv.service;

import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.larin.springmvcjv.model.Customer;
import org.larin.springmvcjv.model.CustomerOrder;
import org.larin.springmvcjv.model.CustomerStatus;
import org.larin.springmvcjv.repository.CustomerRepository;
import org.larin.springmvcjv.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final CustomerRepository customerRepository;

    private final OrderRepository orderRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow();
    }

    @Override
    public Customer createCustomer(String name, String email, String phoneNumber) {
        if (!UserValidation.validateEmail(email)) throw new RuntimeException("Invalid email");
        if (!UserValidation.validatePhone(phoneNumber)) throw new RuntimeException("Invalid phone number");
        Customer customer = Customer.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .customerStatus(CustomerStatus.NEW)
                .customerOrderList(new ArrayList<>())
                .build();
        customerRepository.save(customer);
        return customer;
    }


    @Override
    public Customer updateCustomer(Customer customer) {
        Customer updatedCustomer = getCustomer(customer.getCustomerId());
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setEmail(customer.getEmail());
        updatedCustomer.setCustomerStatus(customer.getCustomerStatus());
        customerRepository.save(customer);
        return updatedCustomer;
    }

    @Override
    public Customer deleteCustomer(Long customerId) {
        Customer customer = getCustomer(customerId);
        customerRepository.delete(customer);
        return customer;
    }

    @Override
    public CustomerOrder createOrder(Long customerId, CustomerOrder order) {
        Customer customer = getCustomer(customerId);
        CustomerOrder newOrder = CustomerOrder.builder()
                .cost(order.getCost())
                .description(order.getDescription())
                .orderDate(LocalDateTime.now())
                .customer(customer)
                .build();
        orderRepository.save(newOrder);
        return newOrder;
    }
}
