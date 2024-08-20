package com.book.write.config;


import com.book.write.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity // 웹 보안 가능하게 함.

public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    MemberService memberService;

    @Autowired
    private  CustomOAuth2UserService customOAuth2UserService;

    @Value("${uploadPath}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 로컬 파일 시스템의 파일을 URL 경로를 통해 제공
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //로그인에 관여
        http.authorizeRequests(auth -> auth
                .requestMatchers("/resources/**", "/css/**", "/js/**", "/images/**", "/img/**", "/item/**", "/favicon.ico", "/error").permitAll()
                .requestMatchers("/", "/member/**", "/writeNovel/**","/write/**","/point/**", "/novel/**", "/coin/**", "/comment/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        ).formLogin(formLogin -> formLogin
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .usernameParameter("loginId")
                .passwordParameter("loginPassword")
                .failureUrl("/member/login/error")
        ).logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/")
        ).oauth2Login(oauthLogin -> oauthLogin
                .defaultSuccessUrl("/member/oauth/new")
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAuth2UserService))
        );

        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(new CustomAuthenticationEntrypoint()));

        return http.build();
    }
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

}