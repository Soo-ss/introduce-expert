package com.api.backend.modules.review;

import com.api.backend.infra.advice.exception.CAccountExistException;
import com.api.backend.infra.advice.exception.CNotOwnerException;
import com.api.backend.infra.advice.exception.CResourceNotExistException;
import com.api.backend.infra.utils.CheckForbiddenWord;
import com.api.backend.infra.utils.cache.CacheKey;
import com.api.backend.infra.utils.cache.CacheService;
import com.api.backend.modules.account.Account;
import com.api.backend.modules.account.AccountRepository;
import com.api.backend.modules.expertClass.ExpertClass;
import com.api.backend.modules.expertClass.ExpertClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AccountRepository accountRepository;
    private final CacheService cacheService;
    private final ExpertClassService expertClassService;

    @Cacheable(value = CacheKey.REVIEWS, key = "#expertClassName", unless = "#result == null")
    public List<Review> findReviews(String expertClassName) {
        return reviewRepository.findByExpertClass(
                expertClassService.getExpertClassTitle(expertClassName)
        );
    }

    public Review getReview(long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(CResourceNotExistException::new);
    }

    // TODO: 게시글 등록후 캐시 삭제
    @CacheEvict(value = CacheKey.REVIEWS, key = "#expertClassName")
    @CheckForbiddenWord
    public Review writeReview(String email, String expertClassName, ParamsReview paramsReview) {
        ExpertClass expertClass = expertClassService.getExpertClassTitle(expertClassName);
        Review newReview = Review.builder()
                .account(accountRepository.findByEmail(email).orElseThrow(CAccountExistException::new))
                .expertClass(expertClass)
                .title(paramsReview.getTitle())
                .content(paramsReview.getContent())
                .build();
        return reviewRepository.save(newReview);
    }

    @CheckForbiddenWord
    public Review updateReview(long reviewId, String email, ParamsReview paramsReview) {
        Review review = getReview(reviewId);
        Account account = review.getAccount();
        if (!email.equals(account.getEmail()))
            throw new CNotOwnerException();

        // TODO: 영속성 컨텍스트의 변경감지(dirty checking) 기능에 의해 조회한 Post내용을 변경만 해도 Update쿼리가 실행됩니다.
        review.setUpdate(paramsReview.getTitle(), paramsReview.getContent());
        cacheService.deleteReviewCache(review.getReviewId(), review.getExpertClass().getTitle());
        return review;
    }

    public boolean deleteReview(long reviewId, String email) {
        Review review = getReview(reviewId);
        Account account = review.getAccount();
        if (!email.equals(account.getEmail()))
            throw new CNotOwnerException();
        reviewRepository.delete(review);
        cacheService.deleteReviewCache(review.getReviewId(), review.getExpertClass().getTitle());

        return true;
    }

    // TODO: AOP 방식 안쓰고 이거 쓰면 중복코드때문에 유지보수 힘들다.
//    public void checkForbiddenWord(String word){
//        List<String> forbiddenWords = Arrays.asList("금칙어", "금칙어2");
//        Optional<String> forbiddenWord = forbiddenWords.stream().filter(word::contains).findFirst();
//        if(forbiddenWord.isPresent()){
//            throw new CForbiddenWordException(forbiddenWord.get());
//        }
//    }
}
