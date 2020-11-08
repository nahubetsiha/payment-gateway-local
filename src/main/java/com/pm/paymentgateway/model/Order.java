package com.pm.paymentgateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "order_table")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CardInformation cardInfo;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable
    private List<PayTo> payTo;
    private Long orderId;
    private String userEmail;
}
