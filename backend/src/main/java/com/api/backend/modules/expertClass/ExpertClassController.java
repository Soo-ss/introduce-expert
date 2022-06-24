package com.api.backend.modules.expertClass;

import com.api.backend.infra.response.ResponseService;
import com.api.backend.infra.response.model.CommonResult;
import com.api.backend.infra.response.model.MultiResult;
import com.api.backend.infra.response.model.ParamsReview;
import com.api.backend.infra.response.model.SingleResult;
import com.api.backend.modules.review.Review;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "엑스퍼트 클래스 정보 조회", notes = "엑스퍼트 클래스 정보를 조회한다.")
    @GetMapping(value = "/{expertClassName}")
    public SingleResult<ExpertClass> expertClassInfo(@PathVariable String expertClassName) {
        return responseService.getSingleResult(expertClassService.findExpertClass(expertClassName));
    }

    @ApiOperation(value = "엑스퍼트 클래스 리뷰 리스트", notes = "엑스퍼트 클래스 리뷰 리스트를 조회한다.")
    @GetMapping(value = "/{expertClassName}/reviews")
    public MultiResult<Review> reviews(@PathVariable String expertClassName) {
        return responseService.getMultiResult(expertClassService.findReviews(expertClassName));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "엑스퍼트 클래스 리뷰 작성", notes = "엑스퍼트 클래스에 리뷰를 작성한다.")
    @PostMapping(value = "/{expertClassName}")
    public SingleResult<Review> review(@PathVariable String expertClassName, @Valid @ModelAttribute ParamsReview review) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return responseService.getSingleResult(expertClassService.writePost(email, expertClassName, review));
    }

    @ApiOperation(value = "엑스퍼트 클래스 리뷰 상세", notes = "엑스퍼트 클래스 리뷰 상세정보를 조회한다.")
    @GetMapping(value = "/review/{reviewId}")
    public SingleResult<Review> review(@PathVariable long reviewId) {
        return responseService.getSingleResult(expertClassService.getReview(reviewId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "엑스퍼트 클래스 리뷰 수정", notes = "엑스퍼트 클래스 리뷰를 수정한다.")
    @PutMapping(value = "/review/{reviewId}")
    public SingleResult<Review> review(@PathVariable long reviewId, @Valid @ModelAttribute ParamsReview review) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return responseService.getSingleResult(expertClassService.updateReview(reviewId, email, review));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "엑스퍼트 클래스 리뷰 삭제", notes = "엑스퍼트 클래스 리뷰를 삭제한다.")
    @DeleteMapping(value = "/review/{reviewId}")
    public CommonResult deleteReview(@PathVariable long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        expertClassService.deleteReview(reviewId, email);
        return responseService.getSuccessResult();
    }
}
