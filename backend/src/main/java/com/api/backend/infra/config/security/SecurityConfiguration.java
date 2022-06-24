package com.api.backend.infra.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*
    TODO: hasRole([role])
    TODO: 현재 로그인된 사용자가 지정된 role을 가지고 있으면 true를 반환합니다.
    TODO: 제공된 role이 'ROLE_'로 시작하지 않으면 기본적으로 'ROLE_'를 추가합니다.
    TODO: 이것은 DefaultWebSecurityExpressionHandler에서 defaultRolePrefix를 수정하여 커스터마이즈할 수 있습니다.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // TODO: rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .csrf().disable() // TODO: rest api이므로 csrf 보안이 필요없으므로 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // TODO: jwt token으로 인증하므로 세션은 필요없으므로 생성안함.
                .and()
                .authorizeRequests() // TODO: 다음 리퀘스트에 대한 사용권한 체크
                .antMatchers("/*/login", "/*/kakao-login/**", "/*/register", "/*/kakao-register/**", "/social/**").permitAll() // TODO: 가입 및 인증 주소는 누구나 접근가능
                .antMatchers(HttpMethod.GET, "/exception/**", "helloworld/**", "/api/expertClass/**").permitAll() // TODO: hellowworld로 시작하는 GET요청 리소스는 누구나 접근가능
//                .antMatchers("/*/users").hasRole("ADMIN") // for test
                .anyRequest().hasRole("USER") // TODO: 그외 나머지 요청은 모두 인증된 회원만 접근 가능, ROLE_USER랑 같은말이다.
                .and()
                .exceptionHandling().accessDeniedHandler(new CAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // TODO: jwt token 필터를 id/password 인증 필터 전에 넣는다

    }

    // TODO: ignore check swagger resource
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");

    }
}
