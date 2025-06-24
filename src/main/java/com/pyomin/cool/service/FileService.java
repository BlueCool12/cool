package com.pyomin.cool.service;

import java.io.InputStream;

public interface FileService {

    byte[] optimize(InputStream inputStream, String originalFilename);

    String save(byte[] data, String originalFilename);
}
