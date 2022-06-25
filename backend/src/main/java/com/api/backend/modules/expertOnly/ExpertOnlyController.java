package com.api.backend.modules.expertOnly;

import com.api.backend.infra.response.ResponseService;
import com.api.backend.infra.response.model.CommonResult;
import com.api.backend.infra.response.model.SingleResult;
import com.api.backend.modules.account.Account;
import com.api.backend.modules.account.AccountRepository;
import com.api.backend.modules.account.form.RegisterForm;
import com.api.backend.modules.expertClass.ExpertClass;
import com.api.backend.modules.expertClass.ExpertClassService;
import com.api.backend.modules.expertClass.ParamsExpertClass;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@Api(tags = {"4. Expert Only"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/expertOnly")
public class ExpertOnlyController {

    private final AccountRepository accountRepository;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final ExpertClassService expertClassService;

    @ApiOperation(value = "가입", notes = "회원가입")
    @PostMapping("/register")
    public CommonResult register(@RequestBody RegisterForm registerForm) {
        Account newAccount = Account.builder()
                .email(registerForm.getEmail())
                .password(passwordEncoder.encode(registerForm.getPassword()))
                .name(registerForm.getName())
                .roles(Arrays.asList("ROLE_ADMIN", "ROLE_USER"))
                .build();
        accountRepository.save(newAccount);

        return responseService.getSuccessResult();
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
}
