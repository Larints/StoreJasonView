package org.larin.springmvcjv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.larin.springmvcjv.model.Customer;
import org.larin.springmvcjv.model.CustomerStatus;
import org.larin.springmvcjv.repository.CustomerRepository;
import org.larin.springmvcjv.repository.OrderRepository;
import org.larin.springmvcjv.service.StoreServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private StoreServiceImpl storeService;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = Customer.builder()
                .customerId(1L)
                .name("John Doe")
                .email("example@mail.com")
                .phoneNumber("123-456-7890")
                .customerStatus(CustomerStatus.NEW)
                .customerOrderList(new ArrayList<>())
                .build();
    }

    @Test
    public void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        List<Customer> customers = storeService.getAllCustomers();
        assertEquals(1, customers.size());
        assertEquals("John Doe", customers.get(0).getName());
    }


}
