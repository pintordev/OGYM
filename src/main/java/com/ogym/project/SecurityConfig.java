package com.ogym.project;


import com.ogym.project.handler.LoginFailHandler;
import com.ogym.project.handler.CustomAuthSuccessHandler;
import com.ogym.project.handler.Oauth2LoginFailureHandler;
import com.ogym.project.user.oauth2Account.Oauth2UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthSuccessHandler loginSuccessHandler;
    private final LoginFailHandler loginFailHandler;
    private final Oauth2UserSecurityService oauth2UserSecurityService;
    private final Oauth2LoginFailureHandler oauth2LoginFailureHandler;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers(
                        new AntPathRequestMatcher("/**")).permitAll()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailHandler)
                .usernameParameter("loginId")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .permitAll()
                // OAuth 로그인
                .and()
                .oauth2Login()
                .loginPage("/user/login")
                .failureHandler(oauth2LoginFailureHandler)
                .defaultSuccessUrl("/")
                .userInfoEndpoint()
                .userService(oauth2UserSecurityService)
        ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
