package com.example.kursinisprojektas.fxControllers;

import com.example.kursinisprojektas.hibernateControllers.GenericHibernate;
import com.example.kursinisprojektas.hibernateControllers.ShopHibernate;
import com.example.kursinisprojektas.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import jakarta.persistence.EntityManagerFactory;

public class OrderChat {
    @FXML
    public TreeView<Comment> messageTree;
    public TextField messageTitle;
    public TextArea messageBody;
    private EntityManagerFactory entityManagerFactory;
    private GenericHibernate genericHibernate;
    private ShopHibernate shopHibernate;
    private User currentUser;
    private Comment comment;
    private Cart cart;

    public void setData(EntityManagerFactory entityManagerFactory, User user, Cart cart) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);
        this.shopHibernate = new ShopHibernate(entityManagerFactory);
        this.cart = cart;
        loadMessages();
    }

    public void createMessage(ActionEvent actionEvent) {
        Cart selectedOrder = genericHibernate.getEntityByID(Cart.class, cart.getId());
        TreeItem<Comment> comment = messageTree.getSelectionModel().getSelectedItem();
        if (comment != null) {
            Comment parentComment = genericHibernate.getEntityByID(Comment.class, messageTree.getSelectionModel().getSelectedItem().getValue().getId());
            Comment replyToComment = new Comment(messageTitle.getText(), messageBody.getText(), currentUser, parentComment);
            parentComment.getReplies().add(replyToComment);
            genericHibernate.update(parentComment);
        }
        else {
            Comment review = new Comment(
                    messageTitle.getText(),
                    messageBody.getText(),
                    currentUser,
                    cart
            );
            selectedOrder.getChat().add(review);
            genericHibernate.update(selectedOrder);
        }
        loadMessages();
    }
    public void loadMessages() {
        Cart selectedOrder = genericHibernate.getEntityByID(Cart.class, cart.getId());
        messageTree.setRoot(new TreeItem<>());
        messageTree.setShowRoot(false);
        messageTree.getRoot().setExpanded(true);

        // Custom cell factory to display comments with Title | Date format
        messageTree.setCellFactory(param -> new TreeCell<>() {
            @Override
            protected void updateItem(Comment comment, boolean empty) {
                super.updateItem(comment, empty);
                if (empty || comment == null) {
                    setText(null);
                } else {
                    setText(comment.getCommentTitle() + "\n" + comment.getCommentBody() + "\n" + comment.getOwner() + " - " + comment.getDateCreated()); // Adjust properties as needed
                }
            }
        });

        // Add comments to the tree
        selectedOrder.getChat().forEach(comment -> addTreeItem(comment, messageTree.getRoot()));
    }
    private void addTreeItem(Comment comment, TreeItem<Comment> parentComment) {
        TreeItem<Comment> treeItem = new TreeItem<>(comment);
        parentComment.getChildren().add(treeItem);
        comment.getReplies().forEach(sub -> addTreeItem(sub, treeItem));
    }
    public void updateMessage() {
        Comment comment = genericHibernate.getEntityByID(Comment.class, messageTree.getSelectionModel().getSelectedItem().getValue().getId());

        if(currentUser.getId() == comment.getOwner().getId()) {
            comment.setCommentTitle(messageTitle.getText());
            comment.setCommentBody(messageBody.getText());
            genericHibernate.update(comment);
        }
        else{
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, "User error","Update error", "You are not the owner of this comment");
        }
        loadMessages();
    }

    public void loadMessageData() {
        Comment comment = genericHibernate.getEntityByID(Comment.class, messageTree.getSelectionModel().getSelectedItem().getValue().getId());
        messageTitle.setText(comment.getCommentTitle());
        messageBody.setText(comment.getCommentBody());
    }
}
