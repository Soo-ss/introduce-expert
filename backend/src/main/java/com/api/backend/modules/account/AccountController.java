package com.api.backend.modules.account;

import com.api.backend.infra.advice.exception.CAccountExistException;
import com.api.backend.infra.advice.exception.CAccountNotFoundException;
import com.api.backend.infra.advice.exception.CEmailLoginFailedException;
import com.api.backend.infra.config.security.JwtTokenProvider;
import com.api.backend.infra.response.ResponseService;
import com.api.backend.infra.response.model.CommonResult;
import com.api.backend.infra.response.model.SingleResult;
import com.api.backend.modules.account.form.LoginForm;
import com.api.backend.modules.account.form.RegisterForm;
import com.api.backend.modules.kakao.KakaoProfile;
import com.api.backend.modules.kakao.KakaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Api(tags = {"1. Account"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final KakaoService kakaoService;

    @ApiOperation(value = "로그인", notes = "이메일로 계정 로그인")
    @PostMapping("/login")
    public SingleResult<String> login(@RequestBody LoginForm loginForm) {
        Account account = accountRepository.findByEmail(loginForm.getEmail())
                .orElseThrow(CEmailLoginFailedException::new);
        if (!passwordEncoder.matches(loginForm.getPassword(), account.getPassword())) {
            throw new CEmailLoginFailedException();
        }

        return responseService.getSingleResult(jwtTokenProvider.createToken(
                String.valueOf(account.getAccountId()),
                account.getRoles()
        ));
    }

    @ApiOperation(value = "가입", notes = "회원가입")
    @PostMapping("/register")
    public CommonResult register(@RequestBody RegisterForm registerForm) {
        Account newAccount = Account.builder()
                .email(registerForm.getEmail())
                .password(passwordEncoder.encode(registerForm.getPassword()))
                .name(registerForm.getName())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        accountRepository.save(newAccount);

        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "카카오 로그인", notes = "카카오 계정으로 로그인")
    @PostMapping("/kakaoLogin/{provider}")
    public SingleResult<String> kakaoSignInProvider(
            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam(value = "카카오 access_token", required = true) @RequestParam String accessToken
    ) {
        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        Account account = accountRepository.findByEmailAndProvider(String.valueOf(profile.getId()), provider)
                .orElseThrow(CAccountNotFoundException::new);
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(account.getAccountId()), account.getRoles()));
    }

    @ApiOperation(value = "카카오 계정 가입", notes = "카카오 계정으로 회원가입")
    @PostMapping("/kakaoRegister/{provider}")
    public CommonResult kakaoSignUpProvider(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
                                            @ApiParam(value = "카카오 access_token", required = true) @RequestParam String accessToken,
                                            @ApiParam(value = "이름", required = true) @RequestParam String name){
        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        Optional<Account> account = accountRepository.findByEmailAndProvider(String.valueOf(profile.getId()), provider);
        if(account.isPresent()){
            throw new CAccountExistException();
        }

        Account newAccount = Account.builder()
                .email(String.valueOf(profile.getId()))
                .provider(provider)
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        accountRepository.save(newAccount);

        return responseService.getSuccessResult();
    }
}
