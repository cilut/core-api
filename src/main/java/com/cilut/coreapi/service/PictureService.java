package com.cilut.coreapi.service;

import com.cilut.coreapi.dto.PictureDto;
import com.cilut.coreapi.entity.Picture;

import java.util.List;
import java.util.UUID;

public interface PictureService {
    Picture savePicture(Picture picture);
    List<PictureDto> getPicturesByUser(String userId);

    PictureDto getPicture(String id);

    List<Picture> allPictures();

    void deletePicture(String id);

    void logicalDeletePicture(String id);
}
