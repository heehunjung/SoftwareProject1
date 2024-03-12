package com.example.Proj1_2019202069.controller;
import com.example.Proj1_2019202069.repository.ImageRepository;
import com.example.Proj1_2019202069.domain.Image;
import com.example.Proj1_2019202069.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ImageController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        List<Image> images = imageService.getAllImages();
        model.addAttribute("data", images);
        return "index";
    }
    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        Image image = new Image(); // 새로운 이미지 객체 생성
        model.addAttribute("image", image);
        return "upload";
    }
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("data") MultipartFile file,
                              @RequestParam("title") String title,
                              @RequestParam("content") String content) {
        imageService.saveImage(file, title, content);
        return "redirect:/";
    }
    @PostMapping("/upload/{id}") //수정에 대한 매핑
    public String updateImage(@RequestParam("data") MultipartFile file,
                              @RequestParam("title") String title,
                              @RequestParam("content") String content,
                              @PathVariable("id") Long id) {
        imageService.updateImage(id, title, content,file);
        return "redirect:/index";
    }
    @GetMapping("/index/{id}")
    public String showImageViewer(@PathVariable("id") Long id, Model model) {
        Image image = imageService.getImage(id);
        model.addAttribute("image", image);
        return "viewer";
    }
    @PostMapping("/viewer/{id}")
    public String deleteImage(@PathVariable Long id,Model model) {
        imageService.deleteImage(id);
        List<Image> images = imageService.getAllImages();
        model.addAttribute("data", images);
        return "redirect:/index";  // 또는 삭제 후 리다이렉트하려는 페이지
    }
    @GetMapping("/viewer/{id}")
    public String editImage(@PathVariable Long id, Model model) {
        Image image = imageService.getImage(id);
        model.addAttribute("image", image);
        return "upload";
    }
}