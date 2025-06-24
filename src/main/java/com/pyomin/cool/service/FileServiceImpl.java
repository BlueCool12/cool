package com.pyomin.cool.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;

    @Override
    public byte[] optimize(InputStream inputStream, String originalFilename) {
        String ext = getFileExtension(originalFilename).toLowerCase();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Thumbnails.of(inputStream)
                    .scale(1.0)
                    .outputQuality(1.0)
                    .outputFormat(ext.replace(".", ""))
                    .toOutputStream(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("이미지 최적화 실패", e);
        }
    }

    @Override
    public String save(byte[] data, String originalFilename) {
        String monthlyDir = getMonthlyDirectory();
        validateUploadDir(monthlyDir);

        String extension = getFileExtension(originalFilename);
        String savedFileName = UUID.randomUUID().toString() + extension;
        Path outputPath = Paths.get(monthlyDir, savedFileName);

        try (OutputStream fileOutputStream = new FileOutputStream(outputPath.toFile())) {
            fileOutputStream.write(data);

            String month = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
            return month + "/" + savedFileName;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }

    private String getMonthlyDirectory() {
        String month = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        return Paths.get(UPLOAD_DIR, month).toString();
    }

    private void validateUploadDir(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private String getFileExtension(String filename) {
        int index = filename.lastIndexOf(".");
        if (index == -1) {
            throw new RuntimeException("유효하지 않은 파일명: 확장자가 없습니다.", null);
        }
        return filename.substring(index);
    }
}
