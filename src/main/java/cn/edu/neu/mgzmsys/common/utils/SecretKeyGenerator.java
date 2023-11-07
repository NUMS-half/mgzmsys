package cn.edu.neu.mgzmsys.common.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        // 创建一个足够长的随机密钥
        SecureRandom random = new SecureRandom();
        byte[] secretKeyBytes = new byte[32]; // 32 bytes = 256 bits
        random.nextBytes(secretKeyBytes);
        String secretKeyBase64 = Base64.getEncoder().encodeToString(secretKeyBytes);

        // 打印出Base64编码后的密钥
        System.out.println("Secret Key (Base64 Encoded): " + secretKeyBase64);
    }
}
