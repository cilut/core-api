package com.cilut.coreapi.service.impl;

import com.cilut.coreapi.dto.CustomerOrderDto;
import com.cilut.coreapi.dto.PictureDto;
import com.cilut.coreapi.entity.CustomerOrder;
import com.cilut.coreapi.entity.Picture;
import com.cilut.coreapi.exceptions.PictureNotFoundException;
import com.cilut.coreapi.exceptions.UserNotFoundException;
import com.cilut.coreapi.repository.PictureRepository;
import com.cilut.coreapi.service.PictureService;
import com.cilut.coreapi.utils.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;

    @Override
    public Picture savePicture(Picture picture) {
        picture.setDate(new Date());
        return this.pictureRepository.save(picture);
    }

    @Override
    public List<PictureDto> getPicturesByUser(String userId) {

        List<Picture> pictures = this.pictureRepository.findByUserId(UUID.fromString(userId));

        List<PictureDto> pictureDtos = new LinkedList<>();
        for (Picture picture : pictures){
            PictureDto pictureDto = ModelMapper.pictureToDto(picture);
            pictureDtos.add(pictureDto);
        }

        return pictureDtos;
    }

    @Override
    public PictureDto getPicture(String id) {
        return ModelMapper.pictureToDto(this.pictureRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new PictureNotFoundException("User with this id: " + id + " not found!")));
    }

    @Override
    public List<Picture> allPictures() {
        return this.pictureRepository.findAll();
    }

    @Override
    public void deletePicture(String id){
        Picture picture = this.pictureRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new PictureNotFoundException("User with this id: " + id + " not found!"));
        picture.setDeleted(true);
        this.pictureRepository.save(picture);
    }

    @Override
    public void logicalDeletePicture(String id){
        Picture picture = this.pictureRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new PictureNotFoundException("User with this id: " + id + " not found!"));
        this.pictureRepository.delete(picture);
    }
}
