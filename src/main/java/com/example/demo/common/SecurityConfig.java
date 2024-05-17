package com.example.demo.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  // パスワードの暗号化
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  InMemoryUserDetailsManager userDetailsService() {
    // 管理者設定
    UserDetails admin = User
        .withUsername("admin")
        .password(passwordEncoder().encode("admin"))
        .roles("ADMIN")
        .build();

    // ユーザー設定
    UserDetails user = User
        .withUsername("user")
        .password(passwordEncoder().encode("user"))
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(admin, user);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        // ログインページの許可設定
        .formLogin(login -> login // フォーム認証を使う
            .defaultSuccessUrl("/user") // 認証成功時のデフォルトの遷移先
            .permitAll())

        // リクエストの許可設定
        .authorizeHttpRequests(authz -> authz
            // index.html の参照権限
            .requestMatchers("/")
            .permitAll()
            // user.html の参照権限
            .requestMatchers("/user")
            .hasAnyRole("USER","ADMIN")
            // /admin.html の参照権限
            .requestMatchers("/admin")
            .hasRole("ADMIN"));

    return http.build();
  }

}
