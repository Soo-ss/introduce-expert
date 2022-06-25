package com.api.backend.infra.utils;

import com.api.backend.modules.review.ParamsReview;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckForbiddenWord {

    String param() default "paramsReview.content";
    Class<?> checkClazz() default ParamsReview.class;
}
