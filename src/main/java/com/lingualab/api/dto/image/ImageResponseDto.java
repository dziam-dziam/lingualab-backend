package com.lingualab.api.dto.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponseDto {
    private UUID id;
    private String imageUrl;
    private String imageKey;
    private String originalFileName;
    private String contentType;
    private Long sizeBytes;
    private Instant uploadedAt;
}
