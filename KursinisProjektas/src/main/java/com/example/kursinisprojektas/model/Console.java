package com.example.kursinisprojektas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public final class Console extends Product{
    @Enumerated
    private consolePlatform consoleType;
    private String consoleManufacturer;
    private LocalDate releaseDate;

    public Console(String title, String description, int qty, consolePlatform consoleType, String consoleManufacturer, LocalDate releaseDate) {
        super(title, description, qty);
        this.consoleType = consoleType;
        this.consoleManufacturer = consoleManufacturer;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return title;
    }
}
