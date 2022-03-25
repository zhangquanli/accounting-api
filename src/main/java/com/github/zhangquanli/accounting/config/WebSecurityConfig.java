package com.github.zhangquanli.accounting.config;

import com.github.zhangquanli.accounting.entity.User;
import com.github.zhangquanli.accounting.repository.UserRepository;
import com.github.zhangquanli.security.configurers.BearerTokenConfigurer;
import com.github.zhangquanli.security.configurers.PasswordLoginConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * 安全框架配置
 *
 * @author zhangquanli
 * @since 2022/3/22 11:26:00
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 处理跨域请求
        http.cors();
        // 开启密码登录功能
        http.apply(new PasswordLoginConfigurer<>());
        // 开启用户认证功能
        http.apply(new BearerTokenConfigurer<>());
        // 开启权限验证功能
        http.authorizeRequests(authorizeRequests ->
                authorizeRequests.anyRequest().authenticated());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("username '" + username + "' cannot be found");
            }
            return user;
        };
    }

}
