package com.cilut.coreapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryImgService {

    String uploadImage(MultipartFile file);
}
