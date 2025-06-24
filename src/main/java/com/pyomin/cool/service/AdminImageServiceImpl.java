package com.pyomin.cool.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyomin.cool.domain.Image;
import com.pyomin.cool.domain.ImageMapping;
import com.pyomin.cool.domain.ImageMapping.TargetType;
import com.pyomin.cool.repository.ImageMappingRepository;
import com.pyomin.cool.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminImageServiceImpl implements AdminImageService {

    private final ImageRepository imageRepository;

    private final ImageMappingRepository imageMappingRepository;

    @Override
    @Transactional
    public String saveImage(String path) {
        Image image = Image.of(path);
        imageRepository.save(image);
        return path;
    }

    @Override
    @Transactional
    public void connectImagesToPost(Long postId, List<String> paths) {
        List<Image> images = getImagesByPaths(paths);

        List<ImageMapping> mappings = images.stream()
                .map(img -> ImageMapping.forPost(img, postId))
                .toList();

        imageMappingRepository.saveAll(mappings);
    }

    @Transactional(readOnly = true)
    public List<Image> getImagesByPaths(List<String> paths) {
        List<Image> images = imageRepository.findByPathIn(paths);

        if (images.size() != paths.size()) {
            throw new RuntimeException("일치하지 않는 이미지가 존재합니다.");
        }

        return images;
    }

    @Override
    @Transactional
    public void deleteMappingsByPostId(Long postId) {
        imageMappingRepository.deleteByTargetIdAndTargetType(postId, TargetType.POST);
    }

}
