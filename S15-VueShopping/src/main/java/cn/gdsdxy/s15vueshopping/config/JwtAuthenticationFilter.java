package cn.gdsdxy.s15vueshopping.config;

import cn.gdsdxy.s15vueshopping.entity.ShopUser; // 确保导入了你的实体类
import cn.gdsdxy.s15vueshopping.util.FwResult;
import cn.gdsdxy.s15vueshopping.util.JwtConstant;
import cn.gdsdxy.s15vueshopping.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Component // 建议用 Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        // OPTIONS 请求直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 1. 获取 Token
        String token = request.getHeader(JwtConstant.HEADER_NAME);

        // 2. [核心修改] 如果没有 Token，直接放行！
        // 不要在这里拦截，交给 SecurityConfig 的 requestMatchers 决定是否需要登录。
        // 这样 /products/detail 这种配置了 permitAll 的接口就能访问了。
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        response.setCharacterEncoding("UTF-8");

        // 3. 校验 Token 格式和签名
        if (!JwtUtil.verifyToken(token)) {
            sendError(response, "token验证失败");
            return;
        }

        // 4. Redis 校验有效期
        Object userObj = redisTemplate.opsForValue().get(token);
        if (userObj == null) {
            sendError(response, "登录过期，请重新登录");
            return;
        }

        // 5. [新增] 告诉 Spring Security 用户已登录
        // 只有这一步做了，Controller 里的 authenticated() 才能通过
        if (userObj instanceof ShopUser) {
            ShopUser user = (ShopUser) userObj;
            // 创建认证信息
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 6. 续期并放行
        redisTemplate.expire(token, 20, TimeUnit.MINUTES);
        filterChain.doFilter(request, response);
    }

    private void sendError(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=utf-8;");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(FwResult.failedMsg(msg));
        response.getWriter().write(result);
    }
}