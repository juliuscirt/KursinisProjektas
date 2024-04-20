package com.example.kursinisprojektas.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class Manager extends User{
    private boolean isAdmin;
    @ManyToOne
    private Warehouse warehouse;
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Cart> myManagedCarts;

    public Manager(String name, String surname, String login, String password, boolean isAdmin) {
        super(name, surname, login, password);
        this.isAdmin = isAdmin;
    }
}
