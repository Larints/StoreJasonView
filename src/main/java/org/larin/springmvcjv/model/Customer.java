package org.larin.springmvcjv.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.larin.springmvcjv.model.view.DetailView;
import org.larin.springmvcjv.model.view.SummaryView;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(SummaryView.class)
    private Long customerId;
    @JsonView(SummaryView.class)
    private String name;
    @JsonView(SummaryView.class)
    private String email;
    @JsonView(SummaryView.class)
    private String phoneNumber;
    @JsonView(SummaryView.class)
    private CustomerStatus customerStatus;
    @JsonView(DetailView.class)
    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<CustomerOrder> customerOrderList;

}
