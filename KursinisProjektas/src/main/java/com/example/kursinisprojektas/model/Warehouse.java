package com.example.kursinisprojektas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String address;
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> productList;
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Manager> employees;
    @Enumerated(EnumType.STRING)
    private City city;
    private LocalDate dateCreated;
    private LocalDate dateModified;

    public Warehouse(String address, City city) {
        this.address = address;
        this.city = city;
        this.dateCreated = LocalDate.now();
        this.dateModified = LocalDate.now();
    }

    @Override
    public String toString() {
        return address + ", " + city + " #" + id;
    }
}
