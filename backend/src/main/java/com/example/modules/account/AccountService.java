package com.example.modules.account;

import com.example.infra.mail.EmailMessage;
import com.example.infra.mail.EmailService;
import com.example.modules.account.form.*;
import com.example.modules.utils.CryptoUtils;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CryptoUtils cryptoUtils;
    private final ModelMapper modelMapper;
    private final TemplateEngine templateEngine;
    private final EmailService emailService;

    public void sendConfirmEmail(Account newAccount) {
        Context context = new Context();
        context.setVariable("link", "/check-email-token?token=" + newAccount.getEmailCheckToken() +
                "&email=" + newAccount.getEmail());
        context.setVariable("nickname", newAccount.getNickname());
        context.setVariable("linkName", "이메일 인증하기");
        context.setVariable("message", "스터디올래 서비스를 이용하려면 링크를 클릭하세요");
        context.setVariable("host", "http://localhost:3000");
        String message = templateEngine.process("mail/mail-template", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .to(newAccount.getEmail())
                .subject("스터디올래, 회원 가입 인증")
                .message(message)
                .build();

        emailService.sendEmail(emailMessage);
    }

    public boolean register(RegisterForm registerForm) {
        registerForm.setPassword(cryptoUtils.hashPassword(registerForm.getPassword()));
        Account account = modelMapper.map(registerForm, Account.class);
        account.generateEmailCheckToken();
        account.setToken("");

        // 개발 편의
        account.setEmailVerified(true);
        accountRepository.save(account);
//            sendConfirmEmail(account);

        return true;
    }

    // 원하는 정보만 선택적으로 넘긴다.
    public JsonObject getMyInfo(Account account, boolean emailVerified) {
        JsonObject obj = new JsonObject();
        obj.addProperty("isAuth", true);
        obj.addProperty("user_id", account.getAccountId());
        obj.addProperty("email", account.getEmail());
        obj.addProperty("nickname", account.getNickname());
        obj.addProperty("emailVerified", emailVerified);
        obj.addProperty("profileImage", account.getProfileImage());

        return obj;
    }

    public JsonObject checkEmailToken(CheckEmailTokenForm checkEmailTokenForm) {
        Account account = accountRepository.findByEmail(checkEmailTokenForm.getEmail());

        JsonObject obj = new JsonObject();
        if (account == null) {
            obj.addProperty("error", "wrongEmail");
            return obj;
        }

        if (!account.isValidToken(checkEmailTokenForm.getToken())) {
            obj.addProperty("error", "wrongToken");
            return obj;
        }

        account.completeSignUp();
        obj.addProperty("error", "noError");
        obj.addProperty("numberOfUser", accountRepository.count());
        obj.addProperty("nickname", account.getNickname());

        return obj;
    }

    public void updateProfile(ProfileForm profileForm) {
        Account account = accountRepository.findByEmail(profileForm.getEmail());
        modelMapper.map(profileForm, account);
        accountRepository.save(account);
    }

    public void updatePassword(PasswordForm passwordForm) {
        Account account = accountRepository.findByEmail(passwordForm.getEmail());
        account.setPassword(cryptoUtils.hashPassword(passwordForm.getNewPassword()));
        accountRepository.save(account);
    }

    public void updateNotifications(Notifications notifications) {
        Account account = accountRepository.findByEmail(notifications.getEmail());
        modelMapper.map(notifications, account);
        accountRepository.save(account);
    }

}
