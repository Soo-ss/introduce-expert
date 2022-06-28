package com.api.backend.infra.utils;

import com.api.backend.infra.advice.exception.CForbiddenWordException;
import io.micrometer.core.instrument.util.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class CheckForbiddenWordAspect {

    @Before(value = "@annotation(checkForbiddenWord)")
    public void forbiddenWordCheck(JoinPoint pjp, CheckForbiddenWord checkForbiddenWord) throws Throwable {
        String[] param = checkForbiddenWord.param().split("\\.");
        String paramName;
        String fieldName = "";
        if (param.length == 2) {
            paramName = param[0];
            fieldName = param[1];
        } else {
            paramName = checkForbiddenWord.param();
        }
        Integer parameterIdx = getParameterIdx(pjp, paramName);
        if (parameterIdx == -1)
            throw new IllegalArgumentException();

        String checkWord;
        if (StringUtils.isNotEmpty(fieldName)) {
            Class<?> clazz = checkForbiddenWord.checkClazz();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            checkWord = (String) field.get(pjp.getArgs()[parameterIdx]);
        } else {
            checkWord = (String) pjp.getArgs()[parameterIdx];
        }
        checkForbiddenWord(checkWord);
    }

    private Integer getParameterIdx(JoinPoint joinPoint, String paramName) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            String parameterName = parameterNames[i];
            if (paramName.equals(parameterName)) {
                return i;
            }
        }
        return -1;
    }

    private void checkForbiddenWord(String word) {
        List<String> forbiddenWords = Arrays.asList("나쁜말", "금칙어", "매우 나쁜말", "너무 나쁜말", "상처받는말");
        Optional<String> forbiddenWord = forbiddenWords.stream().filter(word::contains).findFirst();
        if (forbiddenWord.isPresent())
            throw new CForbiddenWordException(forbiddenWord.get());
    }
}
