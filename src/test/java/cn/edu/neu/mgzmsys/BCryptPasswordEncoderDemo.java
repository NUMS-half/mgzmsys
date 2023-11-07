package cn.edu.neu.mgzmsys;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderDemo {

    public static void main(String[] args) {
        // 创建BCryptPasswordEncoder对象，这里使用默认构造函数，它使用默认的强度
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 原始密码，通常是用户的输入
        String originalPassword = "123456";

        // 使用BCryptPasswordEncoder对原始密码进行加密
        String encodedPassword = passwordEncoder.encode(originalPassword);

        // 打印加密后的密码
        System.out.println("Encoded password: " + encodedPassword);

        // 模拟登录过程中的密码验证
        // 假设用户再次输入了他们的密码，我们需要验证它是否与加密后的密码匹配
        String inputPasswordForLogin = "123456";

        // 使用matches方法来验证原始密码和加密后的密码是否匹配
        boolean isPasswordMatch = passwordEncoder.matches(inputPasswordForLogin, encodedPassword);

        // 打印密码匹配的结果
        System.out.println("Password match: " + isPasswordMatch);
    }
}
