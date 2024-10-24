package org.example.case_modul4.Configuration;

import org.example.case_modul4.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private CustomUserDetailService userService;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/auth/login", "/auth/register", "/static").permitAll()  // Cho phép truy cập trang login và register


                        .requestMatchers("/static/**").permitAll()  // Cho phép mọi người truy cập /static/** mà không cần đăng nhập
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Bảo vệ trang admin
                        .anyRequest().authenticated()  // Tất cả các yêu cầu khác đều cần xác thực
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")  // Định nghĩa trang login
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/static", true)  // Sau khi đăng nhập thành công, điều hướng tới trang static
                        .failureUrl("/auth/login?error=true")  // Trang login khi có lỗi xác thực
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

}
