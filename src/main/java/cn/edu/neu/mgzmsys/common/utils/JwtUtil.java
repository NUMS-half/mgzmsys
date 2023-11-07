package cn.edu.neu.mgzmsys.common.utils;


import java.util.*;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import java.text.ParseException;

public class JwtUtil {

//     毫秒
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2; // 2小时
    // 签名密钥
    private static final String SECRET_KEY = Base64.getEncoder().encodeToString("admin".getBytes());

    /**
     * 创建Token
     */
       public static String createToken(String username, String uid) throws JOSEException {
        // 当前时间
        Date now = new Date();

        // 过期时间设置
        Date expDate = new Date(now.getTime() + EXPIRATION_TIME);

        // 创建JWT的声明
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .claim("username", username)
                .claim("uid", uid)
                .issueTime(now)
                .expirationTime(expDate)
                .build();

        // 头部，指定加密算法为HS256
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                .type(JOSEObjectType.JWT)
                .build();

        // 签名部分，使用密钥签名JWT
        JWSSigner signer = new MACSigner(SECRET_KEY);
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        // 加密
        signedJWT.sign(signer);

        // 序列化JWT为字符串
        return signedJWT.serialize();
    }


     /**
     * 拿到jwt 根据系统密钥，看能不能解开
     * @return
     */
    public static boolean decode(String jwt) throws ParseException, JOSEException {
        //parse() 把字符串转成一个对象
        JWSObject jwsObject=JWSObject.parse(jwt);
        JWSVerifier jwsVerifier=new MACVerifier(SECRET_KEY);
        //解密方法 verify()
        return jwsObject.verify(jwsVerifier);
    }

    /**
     * 根据jwt 获取其中id
     * @param jwt
     * @return
     */
    public static String getUidFromToken(String jwt) throws ParseException {
        //parse() 把字符串转成一个对象,并解密
        JWSObject jwsObject=JWSObject.parse(jwt);
        Payload payload=jwsObject.getPayload();
        Map<String,Object> map=payload.toJSONObject();
        return (String) map.get("uid");
    }
     /**
     * 根据jwt 获取其中载荷部分
     * @param jwt
     * @return
     */
    public static Map getPayLoad(String jwt) throws ParseException {
        //parse() 把字符串转成一个对象,并解密
        JWSObject jwsObject=JWSObject.parse(jwt);
        Payload payload=jwsObject.getPayload();
        Map<String,Object> map=payload.toJSONObject();
        return map;
    }

}
