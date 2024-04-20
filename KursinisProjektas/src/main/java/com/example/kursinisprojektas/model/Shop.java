package com.example.kursinisprojektas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Shop implements Serializable {
    private List<User> users;
    private List<Product> products;

    public Shop() {
        this.products = new ArrayList<>();
        this.users = new ArrayList<>();
    }
}
