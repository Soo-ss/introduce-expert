package com.example.modules.account;

import com.example.modules.account.form.*;
import com.example.modules.utils.CryptoUtils;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    // Controller, Service 구분법?
    /*
    좀 극단적이기는 한데요. 이렇게 딱 생각하시면 좋습니다!
    컨트롤러같은 웹 계층이 없어도 애플리케이션이 동작해야 합니다.
    컨트롤러는 웹 계층을 처리하기 위한 코드만 존재하는 것이 좋습니다.
    예를 들어서 웹 계층이 없이 단순히 메인 메서드를 통해서 콘솔에서만 동작하는
    애플리케이션을 추가로 개발해야 해도 대부분의 서비스 로직을 재사용할 수 있다면 좋겠지요?

    반대로 이야기하면 웹 계층을 위한 폼 데이터를 처리하고,
    화면에 뿌릴 데이터를 모아서 넘겨주고 이런 웹 계층 관련 일들은 모두 컨트롤러에서 담당해야겠지요?
    이런 관점을 접근하시면 어느정도 고민을 덜 수 있을 거에요.
     */

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final CryptoUtils cryptoUtils;

    @GetMapping("/test")
    public String sendTestFile() {
        JsonObject obj = new JsonObject();

        obj.addProperty("sample1", "sample1-data");
        obj.addProperty("sample2", "sample2-data");

        JsonObject data = new JsonObject();
        data.addProperty("data1", "data1-data");
        data.addProperty("data2", 12345);

        obj.add("bigData", data);

        return obj.toString();
    }

    @PostMapping("/register")
    public boolean register(@Valid @RequestBody RegisterForm registerForm) {
        return accountService.register(registerForm);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginForm loginForm) {
        // 프론트의 로컬스토리지 쿠키에 토큰을 저장해야하므로 여기서 토큰을 뱉어줘야함.
        Account account = accountRepository.findByEmail(loginForm.getEmail());

        if (account == null) {
            return "제공된 이메일에 해당하는 유저가 없습니다.";
        }

        boolean checkPassword = cryptoUtils.comparePassword(loginForm.getPassword(), account.getPassword());

        // 비밀번호 불일치
        if (!checkPassword) {
            return "비밀번호가 일치하지 않습니다.";
        }

        // 비밀번호 일치
        String token = cryptoUtils.makeJwt(account.getEmail());
        account.setToken(token);
        accountRepository.save(account);

        return token;
    }

    @PostMapping("/auth")
    public JsonObject auth(@Valid @RequestBody AuthForm authForm) {
        Account account = accountRepository.findByToken(authForm.getToken());

        // 로그아웃
        if (account == null) {
            JsonObject obj = new JsonObject();
            obj.addProperty("isAuth", false);

            return obj;
        }
        // TODO: 토큰 검증 로직 나중에 짜기
//        log.info("[토큰]" + cryptoUtils.checkJwt(authForm.getToken()));

        // 로그인 && 이메일 인증 됐다면
        if (account.getEmailVerified()) {
            return accountService.getMyInfo(account, true);
        }

        // 로그인 && 이메일 인증 X
        return accountService.getMyInfo(account, false);
    }

    @PostMapping("/logout")
    public boolean logout(@Valid @RequestBody AuthForm authForm) {
        Account account = accountRepository.findByToken(authForm.getToken());

        if (account == null) {
            log.info("로그아웃 하려는 유저가 없습니다.");
            return false;
        }

        account.setToken("");
        accountRepository.save(account);

        return true;
    }

    @PostMapping("/check-email-token")
    public JsonObject checkEmailToken(@Valid @RequestBody CheckEmailTokenForm checkEmailTokenForm) {
        return accountService.checkEmailToken(checkEmailTokenForm);
    }
}
