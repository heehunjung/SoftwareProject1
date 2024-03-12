package com.example.Proj1_2019202069.repository;

import com.example.Proj1_2019202069.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByOrderByIdDesc();
}