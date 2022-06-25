package com.api.backend.modules.expertClass;

import com.api.backend.infra.response.ResponseService;
import com.api.backend.infra.response.model.CommonResult;
import com.api.backend.infra.response.model.MultiResult;
import com.api.backend.modules.review.ParamsReview;
import com.api.backend.infra.response.model.SingleResult;
import com.api.backend.modules.review.Review;
import com.api.backend.modules.review.ReviewService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"3. Expert Class"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/expertClass")
public class ExpertClassController {

    private final ExpertClassService expertClassService;
    private final ResponseService responseService;
    private final ReviewService reviewService;

    @ApiOperation(value = "엑스퍼트 클래스 정보 조회", notes = "엑스퍼트 클래스 정보를 조회한다.")
    @GetMapping(value = "/{expertClassTitle}")
    public SingleResult<ExpertClass> expertClassInfo(@PathVariable String expertClassTitle) {
        return responseService.getSingleResult(expertClassService.getExpertClassTitle(expertClassTitle));
    }

    @ApiOperation(value = "엑스퍼트 클래스 리뷰 리스트", notes = "엑스퍼트 클래스 리뷰 리스트를 조회한다.")
    @GetMapping(value = "/{expertClassTitle}/reviews")
    public MultiResult<Review> reviews(@PathVariable String expertClassTitle) {
        return responseService.getMultiResult(reviewService.findReviews(expertClassTitle));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "엑스퍼트 클래스 리뷰 작성", notes = "엑스퍼트 클래스에 리뷰를 작성한다.")
    @PostMapping("/generate")
    public SingleResult<ExpertClass> generateClass(@Valid @RequestBody ParamsExpertClass paramsExpertClass){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return responseService.getSingleResult(expertClassService.generateExpertClass(email, paramsExpertClass));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "엑스퍼트 클래스 리뷰 작성", notes = "엑스퍼트 클래스에 리뷰를 작성한다.")
    @PostMapping(value = "/{expertClassTitle}")
    public SingleResult<Review> review(@PathVariable String expertClassTitle, @Valid @ModelAttribute ParamsReview review) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return responseService.getSingleResult(reviewService.writeReview(email, expertClassTitle, review));
    }

    @ApiOperation(value = "엑스퍼트 클래스 리뷰 상세", notes = "엑스퍼트 클래스 리뷰 상세정보를 조회한다.")
    @GetMapping(value = "/review/{reviewId}")
    public SingleResult<Review> review(@PathVariable long reviewId) {
        return responseService.getSingleResult(reviewService.getReview(reviewId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "엑스퍼트 클래스 리뷰 수정", notes = "엑스퍼트 클래스 리뷰를 수정한다.")
    @PutMapping(value = "/review/{reviewId}")
    public SingleResult<Review> review(@PathVariable long reviewId, @Valid @ModelAttribute ParamsReview review) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return responseService.getSingleResult(reviewService.updateReview(reviewId, email, review));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "엑스퍼트 클래스 리뷰 삭제", notes = "엑스퍼트 클래스 리뷰를 삭제한다.")
    @DeleteMapping(value = "/review/{reviewId}")
    public CommonResult deleteReview(@PathVariable long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        reviewService.deleteReview(reviewId, email);

        return responseService.getSuccessResult();
    }
}
