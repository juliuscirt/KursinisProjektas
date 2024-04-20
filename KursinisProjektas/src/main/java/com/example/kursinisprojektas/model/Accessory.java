package com.example.kursinisprojektas.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Accessory extends Product{
    private String accessoryType;
    private String platformType;
    private String accessoryManufacturer;

    public Accessory(String title, String description, int qty, String accessoryType, String platformType, String accessoryManufacturer) {
        super(title, description, qty);
        this.accessoryType = accessoryType;
        this.platformType = platformType;
        this.accessoryManufacturer = accessoryManufacturer;
    }
    @Override
    public String toString() {
        return title;
    }
}
