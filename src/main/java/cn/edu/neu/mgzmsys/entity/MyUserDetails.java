package cn.edu.neu.mgzmsys.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class MyUserDetails implements UserDetails {
    private String username;
    private String password;
    private String uid;
    private Collection<? extends GrantedAuthority> authorities;

    // 构造方法
    public MyUserDetails(String uid, String username, String password) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.authorities = Collections.emptyList(); // 默认没有权限
    }

    // 必须实现的UserDetails方法
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 账户是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 凭证是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账户是否启用
    @Override
    public boolean isEnabled() {
        return true;
    }

    // uid的getter方法
    public String getUid() {
        return uid;
    }

    // uid的setter方法
    public void setUid(String uid) {
        this.uid = uid;
    }

    // authorities的setter方法
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
