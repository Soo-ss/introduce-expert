package com.api.backend.modules.expertClass;

import com.api.backend.infra.utils.CommonDateEntity;
import com.api.backend.modules.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExpertClass extends CommonDateEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expertClassId;

    @Column(nullable = false)
    private Integer groupCategory;

    @Column(nullable = false)
    private Integer category;

    @Column(nullable = false)
    private Float star;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}