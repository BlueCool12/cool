package com.pyomin.cool.domain;

import java.util.ArrayList;
import java.util.List;

import com.pyomin.cool.dto.CommentUpdateDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    public Comment(Post post, Comment parent, String nickname, String password, String content) {
        this.post = post;
        this.parent = parent;
        this.nickname = nickname;
        this.password = password;
        this.content = content;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admin_id")
    private Long adminId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 4)
    private String password;

    @Column(nullable = false, length = 255)
    private String content;

    @Column(nullable = false)
    private boolean isDeleted = false;

    public void delete() {
        this.isDeleted = true;
    }

    public void update(CommentUpdateDto dto) {
        this.nickname = dto.getNickname();
        this.password = dto.getPassword();
        this.content = dto.getContent();
    }
}
