package com.example.Proj1_2019202069.service;

import com.example.Proj1_2019202069.domain.Image;
import com.example.Proj1_2019202069.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.util.List;
@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;
    public void saveImage(MultipartFile file, String title, String content) {
        try {
            Image image = new Image();
            image.setTitle(title);
            image.setContent(content);
            image.setData(file.getBytes());
            image.setName(file.getOriginalFilename());
            // 이미지를 static/images 내  저장
            Path path = Paths.get("src/main/resources/static/images/" + file.getOriginalFilename());
            Files.write(path, file.getBytes());
            imageRepository.save(image);
        } catch (IOException e) {
            // error
            e.printStackTrace();
        }
    }
    public void updateImage(Long id, String title, String content, MultipartFile file) {
        Image existingImage = imageRepository.findById(id).orElse(null);
        if (existingImage != null) {
            existingImage.setTitle(title);
            existingImage.setContent(content);
            try {
                existingImage.setData(file.getBytes());
                existingImage.setName(file.getOriginalFilename());
                // 이미지를 static/images 내 저장
                Path path = Paths.get("src/main/resources/static/images/" + file.getOriginalFilename());
                Files.write(path, file.getBytes());
            } catch (IOException e) {
                // error
                e.printStackTrace();
            }
            imageRepository.save(existingImage);
        }
    }
    public List<Image> getAllImages() {
        return imageRepository.findAllByOrderByIdDesc();
    }
    public Image getImage(Long id) {
        return imageRepository.findById(id).orElse(null);
    }
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

}
