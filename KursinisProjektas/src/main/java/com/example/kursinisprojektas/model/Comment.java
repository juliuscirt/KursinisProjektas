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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String commentTitle;
    private String commentBody;
    @ManyToOne
    private User owner;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> replies;
    @ManyToOne
    private Comment parentComment;
    @ManyToOne
    private Product review;
    private float rating;
    @ManyToOne
    private Cart chatComment;
    private LocalDate dateCreated;

    public Comment(String commentTitle, String commentBody, User owner, Product review, double rating) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.owner = owner;
        this.review = review;
        this.rating = (int) rating;
        this.dateCreated = LocalDate.now();
    }

    public Comment(String commentTitle, String commentBody, User owner, Comment parentComment) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.owner = owner;
        this.parentComment = parentComment;
        this.dateCreated = LocalDate.now();
    }

    public Comment(String commentTitle, String commentBody, User owner, Cart cart) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.owner = owner;
        this.chatComment = cart;
        this.dateCreated = LocalDate.now();
    }

    @Override
    public String toString() {
        return owner + " - " + String.format("%.1f", rating) + "/5" +"\n" + commentTitle + "\n" + commentBody;
    }
}
