package com.cilut.coreapi.controller;

import com.cilut.coreapi.dto.PictureDto;
import com.cilut.coreapi.entity.Picture;
import com.cilut.coreapi.entity.Picture;
import com.cilut.coreapi.service.OrderService;
import com.cilut.coreapi.service.PictureService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core-api/api/pictures")
@CrossOrigin("*")
public class PictureController {
    private final PictureService pictureService;

    @PostMapping("/save")
    public ResponseEntity<?> savePicture(@RequestBody @Valid Picture picture, HttpServletRequest request) {
        Picture savedPicture = this.pictureService.savePicture(picture);
        return new ResponseEntity<>(savedPicture, HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getPicture(@PathVariable String id){
        PictureDto picture = this.pictureService.getPicture(id);
        return new ResponseEntity<>(picture, HttpStatus.OK);
    }

    @GetMapping("/")
    public List<Picture> allPictures(){
        return this.pictureService.allPictures();
    }

}
