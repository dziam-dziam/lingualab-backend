package com.lingualab.api.repository;

import com.lingualab.api.entity.Image;
import com.lingualab.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    List<Image> findAllByOwnerOrderByUploadedAtDesc(User owner);
}
