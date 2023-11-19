package com.yzpocket.iland.config;

import com.yzpocket.iland.jwt.JwtAuthenticationFilter;
import com.yzpocket.iland.jwt.JwtAuthorizationFilter;
import com.yzpocket.iland.jwt.JwtUtil;
import com.yzpocket.iland.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                                .requestMatchers("/", "/index.html", "/menu.html", "/service.html", "/store.html", "/contact.html").permitAll() // 네비게이션 페이지 요청 모두 접근 허가
                                .requestMatchers("/stores/{storeId}/reviews").permitAll() // 네비게이션 페이지 요청 모두 접근 허가
                                .requestMatchers("/api/auth/**").permitAll() // '/api/auth/'로 시작하는 요청 모두 접근 허가 > 회원가입, 로그인
                                //                                .requestMatchers("/login.html").permitAll()

                                // 메뉴 조회는 누구나 접근 / 카페도 추후 추가
                                .requestMatchers(HttpMethod.GET, "/api/menus").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/menus/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/stores").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/stores/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/stores/{id}/reviews").permitAll()
                                .requestMatchers(HttpMethod.GET, "/stores/{storeId}/reviews").permitAll()
                                .requestMatchers(HttpMethod.GET, "/add-reviews").permitAll()
                                .requestMatchers(HttpMethod.POST, "/add-reviews").permitAll()
                                .requestMatchers(HttpMethod.GET, "/add-menus").permitAll()
                                .requestMatchers(HttpMethod.POST, "/add-menus").permitAll()
                )
                //// 로그아웃 처리 추가 부분
                //.logout(logout -> {
                //    logout.logoutUrl("/api/auth/logout") // 로그아웃을 처리할 URL
                //            //.logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 페이지 (메인 페이지로 리디렉션)
                //            .logoutSuccessHandler(customLogoutSuccessHandler) // CustomLogoutSuccessHandler <- 이부분이 msg : statuscode 반환시킴 설정
                //            .invalidateHttpSession(true) // HTTP 세션 무효화 여부
                //            .deleteCookies(JwtUtil.AUTHORIZATION_HEADER); // 로그아웃 시 삭제할 쿠키 이름
                //})
        ;


        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}