package cn.edu.neu.mgzmsys.common.utils;

import io.jsonwebtoken.*;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    // 毫秒
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24小时
    // 签名密钥
    private static final String SECRET_KEY = Base64.getEncoder().encodeToString("admin".getBytes());

    /**
     * 创建Token
     */
    public static String createToken(String username, String uid) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("username", username)
                .claim("uid", uid)
                .setSubject("login")
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * 校验Token
     */
    public static boolean checkToken(String token) {
        if (token == null) {
            return false;
        }
        try {
            Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * 解析Token
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从Token获取UID
     */
    public static String getUidFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("uid", String.class);
    }
}
