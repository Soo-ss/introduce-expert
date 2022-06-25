package com.api.backend.modules.kakao;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/social/login")
public class KakaoController {

    private final Environment env;
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final KakaoService kakaoService;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    // TODO: 카카오 로그인 페이지
//    @GetMapping
//    public ModelAndView socialLogin(ModelAndView mav){
//        StringBuilder loginUrl = new StringBuilder()
//                .append(env.getProperty("spring.social.kakao.url.login"))
//                .append("?client_id=").append(kakaoClientId)
//                .append("&response_type=code")
//                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirect);
//
//        mav.addObject("loginUrl", loginUrl);
//        mav.setViewName("social/login");
//        return mav;
//    }

    // TODO: 카카오 인증 후 리다이렉트 화면
    @GetMapping("/kakao")
    public ModelAndView redirectKakao(ModelAndView mav, @RequestParam String code){
        ReturnKakaoAuth info = kakaoService.getKakaoTokenInfo(code);
        mav.addObject("authInfo", info);
        mav.setViewName("social/redirectKakao");
        log.info("[info]: {}", info);

        return mav;
    }
}
