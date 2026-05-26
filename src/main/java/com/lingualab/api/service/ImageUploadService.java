package com.lingualab.api.service;

import com.lingualab.api.dto.image.ImageResponseDto;
import com.lingualab.api.entity.Image;
import com.lingualab.api.entity.User;
import com.lingualab.api.mapper.ImageMapper;
import com.lingualab.api.repository.ImageRepository;
import com.lingualab.api.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageUploadService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final CurrentUserService currentUserService;

    @Value("${lingualab.upload-dir}")
    private String uploadDir;

    @Value("${lingualab.backend-url}")
    private String backendUrl;

    @Transactional
    public ImageResponseDto uploadImage(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }
        User owner = currentUserService.getCurrentUser();
        String extension = getExtension(multipartFile.getOriginalFilename());
        String imageKey = UUID.randomUUID() + extension;
        Path uploadPath = Path.of(uploadDir);
        Path filePath = uploadPath.resolve(imageKey);

        try {
            Files.createDirectories(uploadPath);
            multipartFile.transferTo(filePath);
        } catch (IOException exception) {
            throw new IllegalArgumentException("Could not store image");
        }

        Image image = Image.builder()
                .owner(owner)
                .imageKey(imageKey)
                .imageUrl(backendUrl + "/api/uploads/" + imageKey)
                .originalFileName(multipartFile.getOriginalFilename())
                .contentType(multipartFile.getContentType())
                .sizeBytes(multipartFile.getSize())
                .uploadedAt(Instant.now())
                .build();
        return imageMapper.mapEntityToResponseDto(imageRepository.save(image));
    }

    @Transactional(readOnly = true)
    public List<ImageResponseDto> getMyImages() {
        User owner = currentUserService.getCurrentUser();
        return imageRepository.findAllByOwnerOrderByUploadedAtDesc(owner).stream()
                .map(imageMapper::mapEntityToResponseDto)
                .toList();
    }

    private String getExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) return "";
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
