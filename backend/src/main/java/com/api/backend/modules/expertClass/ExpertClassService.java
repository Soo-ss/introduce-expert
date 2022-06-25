package com.api.backend.modules.expertClass;

import com.api.backend.infra.advice.exception.CAccountExistException;
import com.api.backend.infra.advice.exception.CResourceNotExistException;
import com.api.backend.infra.utils.cache.CacheKey;
import com.api.backend.modules.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpertClassService {

    private final ExpertClassRepository expertClassRepository;
    private final AccountRepository accountRepository;

    @Cacheable(value = CacheKey.EXPERT_CLASS, key = "#expertClassTitle", unless = "#result == null")
    public ExpertClass getExpertClassTitle(String expertClassTitle) {
        return Optional.ofNullable(expertClassRepository.findByTitle(expertClassTitle)).orElseThrow(CResourceNotExistException::new);
    }

    public ExpertClass generateExpertClass(String email, ParamsExpertClass expertClass) {
        ExpertClass newExpertClass = ExpertClass.builder()
                .groupCategory(expertClass.getGroupCategory())
                .category(expertClass.getCategory())
                .star(expertClass.getStar())
                .price(expertClass.getPrice())
                .title(expertClass.getTitle())
                .content(expertClass.getContent())
                .account(accountRepository.findByEmail(email).orElseThrow(CAccountExistException::new))
                .build();
        expertClassRepository.save(newExpertClass);

//        ExpertClass nowExpertClass = expertClassService.getExpertClassTitle(expertClass.getTitle());
//        expertClassRepository.save(nowExpertClass.)

        return newExpertClass;
    }
}
