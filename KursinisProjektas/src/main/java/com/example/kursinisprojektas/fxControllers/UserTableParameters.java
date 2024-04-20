package com.example.kursinisprojektas.fxControllers;

import com.example.kursinisprojektas.model.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class UserTableParameters extends User{
    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleStringProperty name = new SimpleStringProperty();
    SimpleStringProperty type = new SimpleStringProperty();
    SimpleStringProperty surname = new SimpleStringProperty();
    SimpleStringProperty login = new SimpleStringProperty();
    SimpleStringProperty password = new SimpleStringProperty();
    SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<>(this,"date");

    public UserTableParameters(SimpleIntegerProperty id, SimpleStringProperty type, SimpleStringProperty name, SimpleStringProperty surname, SimpleStringProperty login, SimpleStringProperty password, SimpleObjectProperty<LocalDate> date) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.date = date;
    }
    public UserTableParameters() {
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getLogin() {
        return login.get();
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }
}
