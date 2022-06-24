package com.api.backend.infra.advice;

import com.api.backend.infra.advice.exception.*;
import com.api.backend.infra.response.ResponseService;
import com.api.backend.infra.response.model.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult defaultException(HttpServletRequest request, Exception e){
        return responseService.getFailResult(Integer.parseInt(getMessage("unKnown.code")), getMessage("unKnown.msg"));
    }

    @ExceptionHandler(CAccountNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult userNotFoundException(HttpServletRequest request, CAccountNotFoundException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(CEmailLoginFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult emailSignInFailed(HttpServletRequest request, CEmailLoginFailedException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("emailSignInFailed.code")), getMessage("emailSignInFailed.msg"));
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    public CommonResult authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult accessDeniedException(HttpServletRequest request, AccessDeniedException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
    }

    @ExceptionHandler(COAuthException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult oauthException(HttpServletRequest request, COAuthException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("oauthError.code")), getMessage("oauthError.msg"));
    }

    @ExceptionHandler(CAccountExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult communicationException(HttpServletRequest request, CAccountExistException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("existingUser.code")), getMessage("existingUser.msg"));
    }

    @ExceptionHandler(CNotOwnerException.class)
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    public CommonResult notOwnerException(HttpServletRequest request, CNotOwnerException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("notOwner.code")), getMessage("notOwner.msg"));
    }

    @ExceptionHandler(CResourceNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResult resourceNotExistException(HttpServletRequest request, CResourceNotExistException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("resourceNotExist.code")), getMessage("resourceNotExist.msg"));
    }

    @ExceptionHandler(CForbiddenWordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult forbiddenWordException(HttpServletRequest request, CForbiddenWordException e){
        return responseService.getFailResult(Integer.parseInt(getMessage("forbiddenWord.code")), getMessage("forbiddenWord.msg"));
    }

    private String getMessage(String code){
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
