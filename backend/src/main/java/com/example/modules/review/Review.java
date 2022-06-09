package com.example.modules.review;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    private Long reviewId;

    private Long expertId;

    private Long userId;

    private Float star;

    private String description;
}
