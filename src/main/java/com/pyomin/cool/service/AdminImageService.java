package com.pyomin.cool.service;

import java.util.List;

public interface AdminImageService {

    String saveImage(String path);

    void connectImagesToPost(Long postId, List<String> paths);

    void deleteMappingsByPostId(Long postId);

}
