package com.api.backend.infra.utils.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheService {

    @Caching(evict = {
            @CacheEvict(value = CacheKey.REVIEW, key = "#reviewId"),
            @CacheEvict(value = CacheKey.REVIEWS, key = "#expertClassTitle")
    })
    public boolean deleteReviewCache(long reviewId, String expertClassTitle){
        log.debug("deleteReviewCache - reviewId {}, expertClassTitle {}", reviewId, expertClassTitle);
        return true;
    }
}
