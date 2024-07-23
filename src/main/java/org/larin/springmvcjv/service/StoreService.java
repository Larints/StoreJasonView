package org.larin.springmvcjv.service;

import org.larin.springmvcjv.model.Customer;
import org.larin.springmvcjv.model.CustomerOrder;

import java.util.List;

public interface StoreService {

    List<Customer> getAllCustomers();

    Customer getCustomer(Long customerId);

    Customer createCustomer(String name, String email, String phoneNumber);

    Customer updateCustomer(Customer customer);

    Customer deleteCustomer(Long customerId);

    CustomerOrder createOrder(Long customerId, CustomerOrder order);

}
