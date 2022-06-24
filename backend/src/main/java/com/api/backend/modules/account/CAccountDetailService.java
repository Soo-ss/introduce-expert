package com.api.backend.modules.account;

import com.api.backend.infra.advice.exception.CAccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CAccountDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public UserDetails loadUserByUsername(String userPk){
        return accountRepository.findById(Long.parseLong(userPk)).orElseThrow(CAccountNotFoundException::new);
    }
}
