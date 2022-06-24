package com.api.backend.modules.review;

import com.api.backend.modules.expertClass.ExpertClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByExpertClass(ExpertClass expertClass);
}
