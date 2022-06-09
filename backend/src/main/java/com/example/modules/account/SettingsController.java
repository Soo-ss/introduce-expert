package com.example.modules.account;

import com.example.modules.account.form.Notifications;
import com.example.modules.account.form.PasswordForm;
import com.example.modules.account.form.ProfileForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final AccountService accountService;
    private final ModelMapper modelMapper;

    @PostMapping("/profile")
    public boolean updateProfile(@Valid @RequestBody ProfileForm profileForm, Errors errors){
        if(errors.hasErrors()){
            return false;
        }

        accountService.updateProfile(profileForm);
        return true;
    }

    @PostMapping("/password")
    public boolean updatePassword(@Valid @RequestBody PasswordForm passwordForm, Errors errors){
        if (errors.hasErrors()){
            return false;
        }

        accountService.updatePassword(passwordForm);
        return true;
    }

    @PostMapping("/notifications")
    public boolean updateNotifications(@Valid @RequestBody Notifications notifications, Errors errors){
        if(errors.hasErrors()){
            return false;
        }

        accountService.updateNotifications(notifications);
        return true;
    }
}
