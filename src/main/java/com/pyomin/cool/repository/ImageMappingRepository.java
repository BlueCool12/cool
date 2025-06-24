package com.pyomin.cool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pyomin.cool.domain.ImageMapping;
import com.pyomin.cool.domain.ImageMapping.TargetType;

public interface ImageMappingRepository extends JpaRepository<ImageMapping, Long> {
    void deleteByTargetIdAndTargetType(Long targetId, TargetType targetType);
}
