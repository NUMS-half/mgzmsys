package cn.edu.neu.mgzmsys.common.utils;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    //毫秒
    private static long time=1000*60*60*24;
    //签名
    private static String signature="admin";

    public static String createToken(String username){
        //构建JWT对象:Jwts.builder()
        JwtBuilder jwtBuilder = Jwts.builder();
        return jwtBuilder
                //header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //payload
                .claim("username", username)
                .setSubject("login")
                //有效时间===>一天=当前时间+24小时
                //获得当前的系统时间：System.currentTimeMillis()
                .setExpiration(new Date(System.currentTimeMillis()+time))
                .setId(UUID.randomUUID().toString())
                //signature
                //注意点：这个签名算法要与前面的算法一致
                .signWith(SignatureAlgorithm.HS256,signature)
                //将前面3个重要信息拼接起来
                .compact();
    }
        /**
     * 校验token是否正确
     */
    public static boolean checkToken(String token){
        if (token==null){
            return false;
        }
        try {
            //解析
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}