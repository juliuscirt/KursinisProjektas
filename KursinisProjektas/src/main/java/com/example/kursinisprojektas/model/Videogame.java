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

public class Videogame extends Product{
    @Enumerated
    private consolePlatform gamePlatform;
    private videogameCategory gameCategory;
    private String gamePublisher;
    private LocalDate releaseDate;

    public Videogame(String title, String description, int qty, consolePlatform gamePlatform, videogameCategory gameCategory, String gamePublisher, LocalDate releaseDate) {
        super(title, description, qty);
        this.gamePlatform = gamePlatform;
        this.gameCategory = gameCategory;
        this.gamePublisher = gamePublisher;
        this.releaseDate = releaseDate;
    }
    @Override
    public String toString() {
        return title;
    }
}
