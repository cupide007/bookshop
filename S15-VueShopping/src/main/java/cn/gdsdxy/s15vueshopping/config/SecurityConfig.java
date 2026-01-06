package cn.gdsdxy.s15vueshopping.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // 1. 注入你的 JWT 过滤器
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login",
                                "/register",
                                "/res/**",
                                "/products/**",      // 确保这里的路径覆盖了 /products/detail
                                "/productReviews",   // 评价列表
                                "/upload",           // 图片上传(如果是公开的)
                                "/swagger-ui/**"     // 如果有文档
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // 2. [关键] 将 JWT 过滤器添加到 UsernamePasswordAuthenticationFilter 之前
                // 这样请求进来会先走你的过滤器解析 Token，然后再走 Security 的权限判断
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}