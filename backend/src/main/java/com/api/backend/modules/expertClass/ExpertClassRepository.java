package com.api.backend.modules.expertClass;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpertClassRepository extends JpaRepository<ExpertClass, Long> {

    ExpertClass findByTitle(String title);
}
