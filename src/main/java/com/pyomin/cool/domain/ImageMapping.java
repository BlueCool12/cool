package com.pyomin.cool.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Image image;

    private Long targetId;

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    public enum TargetType {
        POST
    }

    public ImageMapping(Image image, Long targetId, TargetType targetType) {
        this.image = image;
        this.targetId = targetId;
        this.targetType = targetType;
    }

    public static ImageMapping forPost(Image image, Long postId) {
        return new ImageMapping(image, postId, TargetType.POST);
    }
}
