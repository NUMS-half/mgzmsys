package cn.edu.neu.mgzmsys.controller;


import cn.edu.neu.mgzmsys.entity.HttpResponseEntity;
import cn.edu.neu.mgzmsys.service.IChildService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 儿童 前端控制器
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
@RestController
@RequestMapping("/child")
public class ChildController {

    @Resource
    private IChildService childService;

    @PostMapping (value = "/login", headers = "Accept=application/json")
    public HttpResponseEntity login(@RequestBody String username, @RequestBody String password) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            if ( username == null || password == null ) {
                throw new NullPointerException();
            }
            boolean login = childService.login(username, password);
            if ( login ) {
                httpResponseEntity.setCode("1");
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage("登录成功");
            } else {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage("用户名或密码错误");
            }
        } catch ( Exception e ) {
            httpResponseEntity.setCode("-1");
            httpResponseEntity.setData(null);
            httpResponseEntity.setMessage("登录时发生异常，请稍后重试");
        }
        return httpResponseEntity;
    }
}

