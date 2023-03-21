package com.github.zhangquanli.accounting.config;

import com.github.zhangquanli.accounting.entity.base.User;
import com.github.zhangquanli.accounting.entity.base.UserRelRole;
import com.github.zhangquanli.accounting.repository.UserRepository;
import com.github.zhangquanli.security.configurers.BearerTokenConfigurer;
import com.github.zhangquanli.security.configurers.OAuth2AuthorizationServerConfigurer;
import com.github.zhangquanli.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import com.github.zhangquanli.security.oauth2.server.authorization.OAuth2Authorization;
import com.github.zhangquanli.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 安全框架配置
 *
 * @author zhangquanli
 * @since 2023/3/19
 */
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(withDefaults());
        http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated());
        http.apply(new OAuth2AuthorizationServerConfigurer<>());
        http.apply(new BearerTokenConfigurer<>());
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
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

    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService(UserRepository userRepository) {
        return new MyOAuth2AuthorizationService(userRepository);
    }

    public static class MyOAuth2AuthorizationService extends InMemoryOAuth2AuthorizationService {
        private final UserRepository userRepository;

        public MyOAuth2AuthorizationService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public void save(OAuth2Authorization authorization) {
            super.save(authorization);
            String username = authorization.getPrincipalName();
            User user = userRepository.findByUsername(username);
            List<UserRelRole> userRelRoles = user.getUserRelRoles();
            if (userRelRoles.size() > 0) {
                LoginUser loginUser = LoginUser.builder()
                        .username(username)
                        .currentUser(user)
                        .currentRole(userRelRoles.get(0))
                        .build();
                LoginUserCache.set(username, loginUser);
            }
        }
    }
}
