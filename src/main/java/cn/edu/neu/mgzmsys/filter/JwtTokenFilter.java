package cn.edu.neu.mgzmsys.filter;

import cn.edu.neu.mgzmsys.common.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.core.annotation.Order;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Order

public class JwtTokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // 如果是OPTIONS请求，则直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())|| request.getRequestURI().endsWith("/login")|| request.getRequestURI().endsWith("/register")) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            try {
                String token = request.getHeader("token");
                if (JwtUtil.checkToken(token)&&token.equals("")) {
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                }
            } catch (ExpiredJwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token is expired");
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token is invalid");
            }
        }
    }
}
