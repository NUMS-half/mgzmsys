package cn.edu.neu.mgzmsys.controller;


import cn.edu.neu.mgzmsys.common.utils.JwtUtil;
import cn.edu.neu.mgzmsys.entity.Child;
import cn.edu.neu.mgzmsys.entity.HttpResponseEntity;
import cn.edu.neu.mgzmsys.entity.Volunteer;
import cn.edu.neu.mgzmsys.service.IChildService;
import cn.edu.neu.mgzmsys.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private PasswordEncoder passwordEncoder;

//    @PostMapping(value = "/login", headers = "Accept=application/json")
//    public HttpResponseEntity login(@RequestBody Map<String,Object> map) {
//        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//        String username=map.get("username").toString();
//        String password=map.get("password").toString();
//        try{
//            if ( username == null || password == null ) {
//                throw new NullPointerException();
//            }
//            String uid = userService.login(username, password);
//            if ( uid!=null ) {
//                httpResponseEntity.setToken(JwtUtil.createToken(username,uid));
//                httpResponseEntity.setCode("1");
//                httpResponseEntity.setData(null);
//                httpResponseEntity.setMessage("登录成功");
//            } else {
//                httpResponseEntity.setCode("0");
//                httpResponseEntity.setData(null);
//                httpResponseEntity.setMessage("用户名或密码错误");
//            }
//        } catch ( Exception e ) {
//            httpResponseEntity.setCode("-1");
//            httpResponseEntity.setData(null);
//            httpResponseEntity.setMessage("登录时发生异常，请稍后重试");
//        }
//        return httpResponseEntity;
//    }

    @PostMapping(value = "/register", headers = "Accept=application/json")
    public HttpResponseEntity register(@RequestBody Map<String, Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            if (map == null) {
                throw new NullPointerException();
            }
            String type = (String) map.get("type");
             String encodedPassword = passwordEncoder.encode((String) map.get("password"));
             boolean result = false;
             //根据map new一个child对象
            if (type.equals("child")) {
                // 注册儿童
                // 注册逻辑...
                // 确保密码是加密过的
                Child child = new Child();
                child.setUserName((String) map.get("username"));
                child.setPassword(encodedPassword);
                child.setChildName((String) map.get("childName"));
                child.setBirthday(LocalDate.parse( (String)map.get("birthday")));
                child.setHobby((String) map.get("hobby"));
                child.setAddress( (String)map.get("address"));
                child.setGender((Integer) map.get("gender"));
                child.setPhone((String) map.get("phone"));
                child.setDescription((String) map.get("description"));
                result = userService.register(child);
            } else if (type.equals("volunteer")) {
                // 注册志愿者
                 Volunteer volunteer = new Volunteer();
                result = userService.register(volunteer);
            }
            if (result) {
                return HttpResponseEntity.REGISTER_SUCCESS;
            } else {
                return HttpResponseEntity.REGISTER_FAIL;
            }
        } catch (Exception e) {
            // 异常处理...
            return HttpResponseEntity.ERROR;
        }
    }

    /**
     * 修改密码
     *
     * @return 修改是否成功
     */
    @PostMapping(value = "/updatePassword", headers = "Accept=application/json")
    public ResponseEntity<HttpResponseEntity> updatePassword(@RequestBody String password, @RequestHeader("token") String jwt) throws ParseException {
        // 使用Spring Security的Authentication对象来获取当前用户

        String userId = JwtUtil.getUidFromToken(jwt);

        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            // 加密新密码
            String encodedNewPassword = passwordEncoder.encode(password);
            boolean result = userService.updatePassword(userId, encodedNewPassword);
            // 更新密码逻辑...
              if ( result ) {
               return HttpResponseEntity.UPDATE_SUCCESS.toResponseEntity();
            } else {
                return HttpResponseEntity.UPDATE_FAIL.toResponseEntity();
            }
        } catch (Exception e) {
            // 异常处理...
            return HttpResponseEntity.ERROR.toResponseEntity();
        }
    }
}

