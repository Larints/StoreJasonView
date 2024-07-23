package org.larin.springmvcjv;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.larin.springmvcjv.contoller.StoreController;
import org.larin.springmvcjv.model.Customer;
import org.larin.springmvcjv.model.CustomerOrder;
import org.larin.springmvcjv.model.CustomerStatus;
import org.larin.springmvcjv.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StoreController.class)
public class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    @Autowired
    private  ObjectMapper objectMapper;

    private Customer customer;

    private CustomerOrder customerOrder;


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
        customerOrder = CustomerOrder.builder()
                .id(1L)
                .cost(20.0)
                .description("test")
                .orderDate(LocalDateTime.now())
                .customer(customer)
                .build();
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        List<Customer> customers = Collections.singletonList(customer);
        when(storeService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/store/customers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("example@mail.com"))
                .andExpect(jsonPath("$[0].phoneNumber").value("123-456-7890"))
                .andExpect(jsonPath("$[0].customerOrderList").doesNotExist());
    }

    @Test
    public void testGetCustomerWithDetailView() throws Exception {
        when(storeService.getCustomer(1L)).thenReturn(customer);

        mockMvc.perform(get("/api/v1/store/customers/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("example@mail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("123-456-7890"))
                .andExpect(jsonPath("$.customerOrderList").exists());
    }

    @Test
    public void testCreateCustomer() throws Exception {
        when(storeService.createCustomer(anyString(), anyString(), anyString())).thenReturn(customer);
        mockMvc.perform(post("/api/v1/store/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        when(storeService.updateCustomer(any(Customer.class))).thenReturn(customer);
        mockMvc.perform(put("/api/v1/store/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        when(storeService.deleteCustomer(1L)).thenReturn(customer);

        mockMvc.perform(delete("/api/v1/store/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testCreateOrder() throws Exception {
        when(storeService.createOrder(anyLong(), any(CustomerOrder.class))).thenReturn(customerOrder);

        mockMvc.perform(post("/api/v1/store/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("test"));
    }

}
