package com.api.backend.modules.expertClass;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ParamsExpertClass {

    @ApiModelProperty(value = "그룹 카테고리", required = true)
    private Integer groupCategory;

    @ApiModelProperty(value = "카테고리", required = true)
    private Integer category;

    @ApiModelProperty(value = "평점", required = true)
    private Float star;

    @ApiModelProperty(value = "가격", required = true)
    private Integer price;

    @NotEmpty
    @ApiModelProperty(value = "제목", required = true)
    private String title;

    @Size(min = 2, max = 500)
    @ApiModelProperty(value = "내용", required = true)
    private String content;
}
