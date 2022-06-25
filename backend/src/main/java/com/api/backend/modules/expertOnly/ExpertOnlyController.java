package com.api.backend.modules.expertOnly;

import com.api.backend.infra.response.ResponseService;
import com.api.backend.infra.response.model.CommonResult;
import com.api.backend.infra.response.model.SingleResult;
import com.api.backend.modules.account.Account;
import com.api.backend.modules.account.AccountRepository;
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
    public CommonResult register(@ApiParam(value = "계정 이메일", required = true) @RequestParam String email,
                                 @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                                 @ApiParam(value = "이름", required = true) @RequestParam String name) {
        Account newAccount = Account.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .roles(Arrays.asList("ROLE_ADMIN", "ROLE_USER"))
                .build();
        accountRepository.save(newAccount);

        return responseService.getSuccessResult();
    }
}
