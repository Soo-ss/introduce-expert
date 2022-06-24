package com.api.backend.modules.account;

import com.api.backend.infra.advice.exception.CAccountNotFoundException;
import com.api.backend.infra.response.ResponseService;
import com.api.backend.infra.response.model.CommonResult;
import com.api.backend.infra.response.model.MultiResult;
import com.api.backend.infra.response.model.SingleResult;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. Login Account"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginAccountController {

    private final AccountRepository accountRepository;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 조회", notes = "모든 회원을 조회한다.")
    @GetMapping("/users")
    public MultiResult<Account> findAllUser(){
        return responseService.getMultiResult(accountRepository.findAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "회원번호(msrl)로 회원을 조회한다.")
    @GetMapping("/user")
    public SingleResult<Account> findUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return responseService.getSingleResult(accountRepository
                .findByEmail(email)
                .orElseThrow(CAccountNotFoundException::new));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다.")
    @PutMapping(value = "/user")
    public SingleResult<Account> modify(
            @ApiParam(value = "회원번호", required = true) @RequestParam long accountId,
            @ApiParam(value = "회원이름", required = true) @RequestParam String name
    ){
        Account account = Account.builder()
                .accountId(accountId)
                .name(name)
                .build();
        return responseService.getSingleResult(accountRepository.save(account));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다.")
    @DeleteMapping(value = "/user/{accountId}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable long accountId
    ){
        accountRepository.deleteById(accountId);
        return responseService.getSuccessResult();
    }
}
