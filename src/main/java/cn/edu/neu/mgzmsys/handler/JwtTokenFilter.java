package cn.edu.neu.mgzmsys.handler;

import cn.edu.neu.mgzmsys.common.utils.JwtUtil;
import cn.edu.neu.mgzmsys.entity.MyUserDetails;
import cn.edu.neu.mgzmsys.service.impl.SecurityService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private SecurityService securityService;

    /**
     * StringRedisTemplate和RedisTemplate
     */
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        /**
         *  * 1、判断请求头是否携带jwt
         *  *   否：放行不处理
         *  *   是：走到第二步
         */
        String jwt = httpServletRequest.getHeader("jwt");
        if (jwt == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);//交给过滤器处理
            return;
        }
        /**
         *  * 2、对前端传过来的jwt解密
         *  *   否：放行不处理
         *  *   是：走到第三步
         */
        if (!JwtUtil.decode(jwt)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        Map payLoad = JwtUtil.getPayLoad(jwt);
        String username = (String) payLoad.get("username");

        //把用户信息放到security容器中
        MyUserDetails userDetails = securityService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());
        //把信息放到security容器中
        SecurityContextHolder.getContext().setAuthentication(upa);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 数据加密类
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

