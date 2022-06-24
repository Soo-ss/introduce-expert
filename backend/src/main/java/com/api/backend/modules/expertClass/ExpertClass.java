package com.api.backend.modules.expertClass;

import com.api.backend.infra.utils.CommonDateEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
public class ExpertClass extends CommonDateEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expertClassId;

    @Column(nullable = false, length = 100)
    private String name;
}