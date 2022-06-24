package com.api.backend.infra.utils.cache;

public class CacheKey {

    public static final int DEFAULT_EXPIRE_SEC = 60;
    public static final String USER = "user";
    public static final int USER_EXPIRE_SEC = 60 * 5;
    public static final String EXPERT_CLASS = "expert_class";
    public static final int EXPERT_CLASS_EXPIRE_SEC = 60 * 10;
    public static final String REVIEW = "review";
    public static final String REVIEWS = "reviews";
    public static final int REVIEW_EXPIRE_SEC = 60 * 5;
}
