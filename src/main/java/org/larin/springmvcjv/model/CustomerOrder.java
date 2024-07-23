package org.larin.springmvcjv.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.larin.springmvcjv.model.view.DetailView;
import org.larin.springmvcjv.model.view.SummaryView;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(DetailView.class)
    private Long id;
    @JsonView(DetailView.class)
    private double cost;
    @JsonView(DetailView.class)
    private String description;
    @JsonView(DetailView.class)
    private LocalDateTime orderDate;
    @ManyToOne
    @JoinColumn(name = "customerId")
    @JsonBackReference
    private Customer customer;

}
