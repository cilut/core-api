package com.cilut.coreapi.repository;

import com.cilut.coreapi.entity.CustomerOrder;
import com.cilut.coreapi.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PictureRepository extends JpaRepository<Picture, UUID> {
    List<Picture> findByUserId (UUID userId);

}
