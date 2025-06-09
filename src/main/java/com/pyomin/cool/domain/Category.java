package com.pyomin.cool.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        this.posts.add(post);
        if (!post.getCategories().contains(this)) {
            post.getCategories().add(this);
        }
    }

    public void removePost(Post post) {
        this.posts.remove(post);
        post.getCategories().remove(this);
    }
}
