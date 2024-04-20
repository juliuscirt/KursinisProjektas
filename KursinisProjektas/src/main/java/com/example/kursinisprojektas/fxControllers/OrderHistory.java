package com.example.kursinisprojektas.fxControllers;

import com.example.kursinisprojektas.hibernateControllers.GenericHibernate;
import com.example.kursinisprojektas.hibernateControllers.ShopHibernate;
import com.example.kursinisprojektas.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class OrderHistory {
    public ListView<Cart> completedOrders;
    public ListView<Product> productList;
    public User currentUser;
    private EntityManagerFactory entityManagerFactory;
    private GenericHibernate genericHibernate;

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);
        loadCompletedOrders();
    }
    public void loadProducts() {
        Cart cart = completedOrders.getSelectionModel().getSelectedItem();
        productList.getItems().clear();
        productList.getItems().addAll(cart.getProductList());
    }

    public void loadCompletedOrders(){
        completedOrders.getItems().addAll(genericHibernate.getAllRecords(Cart.class));
        completedOrders.getItems().removeIf(c -> c.getCustomer().getId() != currentUser.getId());
        completedOrders.getItems().removeIf(c -> !Objects.equals(c.getOrderStatus().toString(), "COMPLETED"));
        completedOrders.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Cart cart, boolean empty) {
                super.updateItem(cart, empty);
                if (empty || cart == null) {
                    setText(null);
                } else {
                    setText("Order #" + cart.getId());
                }
            }
        });
    }
}
