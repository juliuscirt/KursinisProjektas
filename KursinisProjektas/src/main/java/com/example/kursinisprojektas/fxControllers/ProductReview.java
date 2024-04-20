package com.example.kursinisprojektas.fxControllers;

import com.example.kursinisprojektas.hibernateControllers.*;
import com.example.kursinisprojektas.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductReview {
    @FXML
    public ListView<Product> productReviewList;
    @FXML
    public TreeView<Comment> productReviewTree;
    public Slider reviewRating;
    public TextField reviewTitle;
    public TextArea reviewBody;
    private EntityManagerFactory entityManagerFactory;
    private User currentUser;
    private GenericHibernate genericHibernate;
    private ShopHibernate shopHibernate;
    private Comment comment;

    public void previewProduct() {
        Product product = genericHibernate.getEntityByID(Product.class, productReviewList.getSelectionModel().getSelectedItem().getId());
        if (product instanceof Console console) {
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Product Information",console.getTitle(), console.toString());
        }
        if (product instanceof Videogame videogame) {
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Product Information",videogame.getTitle(), videogame.toString());
        }
        if (product instanceof Accessory accessory) {
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Product Information",accessory.getTitle(), accessory.toString());
        }
    }

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);
        this.shopHibernate = new ShopHibernate(entityManagerFactory);
        productReviewList.getItems().addAll(genericHibernate.getAllRecords(Product.class));
    }

    public void addReview() {
        Product selectedProduct = genericHibernate.getEntityByID(Product.class, productReviewList.getSelectionModel().getSelectedItem().getId());
        TreeItem<Comment> comment = productReviewTree.getSelectionModel().getSelectedItem();
        if (comment != null) {
            Comment parentComment = genericHibernate.getEntityByID(Comment.class, productReviewTree.getSelectionModel().getSelectedItem().getValue().getId());
            Comment replyToComment = new Comment(reviewTitle.getText(), reviewBody.getText(), currentUser, parentComment);
            parentComment.getReplies().add(replyToComment);
            genericHibernate.update(parentComment);
        }
        else {
            Comment review = new Comment(
                    reviewTitle.getText(),
                    reviewBody.getText(),
                    currentUser,
                    selectedProduct,
                    reviewRating.getValue()
            );
            selectedProduct.getComments().add(review);
            genericHibernate.update(selectedProduct);
        }
    }

    public void deleteReview() {
        Comment comment = genericHibernate.getEntityByID(Comment.class, productReviewTree.getSelectionModel().getSelectedItem().getValue().getId());

        if(currentUser.getId() == comment.getOwner().getId() || currentUser.getClass().getSimpleName().equals("Manager")) {
            shopHibernate.deleteComment(productReviewTree.getSelectionModel().getSelectedItem().getValue().getId());
        }
        else{
            FxUtils.generateAlert(Alert.AlertType.INFORMATION,"User error" ,"Deletion error", "You are not the owner of this comment");
        }
        loadReviews();
    }

    public void updateReview() {
        Comment comment = genericHibernate.getEntityByID(Comment.class, productReviewTree.getSelectionModel().getSelectedItem().getValue().getId());

        if(currentUser.getId() == comment.getOwner().getId()) {
            comment.setRating((float) reviewRating.getValue());
            comment.setCommentTitle(reviewTitle.getText());
            comment.setCommentBody(reviewBody.getText());
            genericHibernate.update(comment);
        }
        else{
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, "User error","Update error", "You are not the owner of this comment");
        }
        loadReviews();
    }

    public void loadReviews() {
        Product selectedProduct = genericHibernate.getEntityByID(Product.class, productReviewList.getSelectionModel().getSelectedItem().getId());
        productReviewTree.setRoot(new TreeItem<>());
        productReviewTree.setShowRoot(false);
        productReviewTree.getRoot().setExpanded(true);
        selectedProduct.getComments().forEach(comment -> addTreeItem(comment, productReviewTree.getRoot()));
    }

    private void addTreeItem(Comment comment, TreeItem<Comment> parentComment) {
        TreeItem<Comment> treeItem = new TreeItem<>(comment);
        parentComment.getChildren().add(treeItem);
        comment.getReplies().forEach(sub -> addTreeItem(sub, treeItem));
    }

    public void viewReview() {
        Comment comment = genericHibernate.getEntityByID(Comment.class, productReviewTree.getSelectionModel().getSelectedItem().getValue().getId());
        reviewRating.setValue(comment.getRating());
        reviewTitle.setText(comment.getCommentTitle());
        reviewBody.setText(comment.getCommentBody());
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the min, max, and default value for the slider
        reviewRating.setMin(0);
        reviewRating.setMax(5);
        reviewRating.setValue(0); // Set the default value here if needed
    }
}
