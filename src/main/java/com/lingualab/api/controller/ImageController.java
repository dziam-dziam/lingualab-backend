package com.lingualab.api.controller;

import com.lingualab.api.dto.image.ImageResponseDto;
import com.lingualab.api.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageUploadService imageUploadService;

    @PostMapping
    public ImageResponseDto upload(@RequestParam("file") MultipartFile multipartFile) {
        return imageUploadService.uploadImage(multipartFile);
    }

    @GetMapping
    public List<ImageResponseDto> getAll() {
        return imageUploadService.getMyImages();
    }
}
