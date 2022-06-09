package com.example.modules.expertClass;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class ExpertClass {

    @Id
    @GeneratedValue
    private Long classId;

    private Integer groupCategory;

    private Integer category;

    private Float star;

    private Integer price;

    private String title;

    private String description;

    private Long accountId;
}
