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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String name;
    protected String surname;
    protected String login;
    protected String password;
    protected LocalDate dateCreated;
    protected LocalDate dateModified;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<Comment> comments;

    public User(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.dateCreated = LocalDate.now();
    }

    @Override
    public String toString() {
        return login;
    }

}
