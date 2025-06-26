package com.pyomin.cool.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    byte[] optimize(InputStream inputStream, String originalFilename);

    String save(byte[] data, String originalFilename);

    ByteArrayInputStream generateSitemapZip(int chunkSize) throws IOException;
}
