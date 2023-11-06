package cn.edu.neu.mgzmsys.common.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // ... 其他配置 ...
            .cors() // 添加CORS配置
            .and()
            .csrf().disable() // 禁用CSRF保护
            // ... 其他配置 ...
            ;
    }
}
