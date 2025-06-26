package com.pyomin.cool.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pyomin.cool.dto.user.PostListDto;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;

    private final UserPostService postService;

    @Override
    public ByteArrayInputStream generateSitemapZip(int chunkSize) throws IOException {
        ByteArrayOutputStream zipOut = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(zipOut);

        List<PostListDto> posts = postService.getAllPosts(null);

        int total = posts.size();
        int fileIndex = 1;

        for (int i = 0; i < total; i += chunkSize) {
            List<PostListDto> chunk = posts.subList(i, Math.min(i + chunkSize, total));
            String fileName = "sitemap-" + fileIndex + ".xml";
            StringBuilder sb = new StringBuilder();

            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            sb.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");

            for (PostListDto post : chunk) {
                sb.append("  <url>\n");
                sb.append("    <loc>https://pyomin.com/posts/")
                        .append(URLEncoder.encode(post.getSlug(), StandardCharsets.UTF_8))
                        .append("</loc>\n");

                String updatedAt = post.getUpdatedAt();
                String lastmod = updatedAt.contains("T") ? updatedAt.split("T")[0] : updatedAt;

                sb.append("    <lastmod>").append(lastmod).append("</lastmod>\n");
                sb.append("  </url>\n");
            }

            sb.append("</urlset>");

            zos.putNextEntry(new ZipEntry(fileName));
            zos.write(sb.toString().getBytes(StandardCharsets.UTF_8));
            zos.closeEntry();

            fileIndex++;
        }

        zos.close(); // finalize zip
        return new ByteArrayInputStream(zipOut.toByteArray());
    }

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
