package com.api.backend.modules.review;

import com.api.backend.infra.utils.CommonDateEntity;
import com.api.backend.modules.account.Account;
import com.api.backend.modules.expertClass.ExpertClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
public class Review extends CommonDateEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false, length = 50)
    private String author;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_class_id")
    private ExpertClass expertClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    // TODO: Join 테이블이 Json 결과에 표시되지 않도록 처리.
    @JsonIgnore
    public ExpertClass getExpertClass() {
        return expertClass;
    }

    public Review(Account account, ExpertClass expertClass, String author, String title, String content) {
        this.account = account;
        this.expertClass = expertClass;
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public Review setUpdate(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;

        return this;
    }
}