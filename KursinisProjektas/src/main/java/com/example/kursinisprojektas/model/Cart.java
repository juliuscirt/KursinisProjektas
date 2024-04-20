package com.example.kursinisprojektas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> productList;
    @ManyToOne
    private Manager manager;
    @ManyToOne
    private Customer customer;
    private LocalDate dateCreated;
    @OneToMany(mappedBy = "chatComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> chat;
    @Enumerated
    private OrderStatus orderStatus;

    public Cart(List<Product> productList, Customer customer) {
        this.productList = productList;
        this.customer = customer;
        this.dateCreated = LocalDate.now();
        this.chat = new ArrayList<>();
        orderStatus = OrderStatus.PREPARING;
    }

    @Override
    public String toString() {
        return "Order #" + id + " " + "Status: " + orderStatus;
    }
}
