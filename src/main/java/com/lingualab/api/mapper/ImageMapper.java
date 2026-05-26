package com.lingualab.api.mapper;

import com.lingualab.api.dto.image.ImageResponseDto;
import com.lingualab.api.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
public class ImageMapper {

    public ImageResponseDto mapEntityToResponseDto(Image entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        return ImageResponseDto.builder()
                .id(entity.getId())
                .imageUrl(entity.getImageUrl())
                .imageKey(entity.getImageKey())
                .originalFileName(entity.getOriginalFileName())
                .contentType(entity.getContentType())
                .sizeBytes(entity.getSizeBytes())
                .uploadedAt(entity.getUploadedAt())
                .build();
    }
}
