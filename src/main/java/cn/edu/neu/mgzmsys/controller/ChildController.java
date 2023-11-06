package cn.edu.neu.mgzmsys.controller;


import cn.edu.neu.mgzmsys.common.utils.JwtUtil;
import cn.edu.neu.mgzmsys.entity.Child;
import cn.edu.neu.mgzmsys.entity.HttpResponseEntity;
import cn.edu.neu.mgzmsys.service.IChildService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    /**
     * 根据儿童id获取儿童信息
     * @return 儿童信息
     */
    @GetMapping(value = "/getChildById", headers = "Accept=application/json")
    public HttpResponseEntity getChildById(@RequestHeader ("token")String token) {

        String id= JwtUtil.getUidFromToken(token);
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            if ( id == null ){
                throw new NullPointerException();
            }
            cn.edu.neu.mgzmsys.entity.Child child = childService.selectChildInfo(id);
            httpResponseEntity.setCode("1");
            httpResponseEntity.setData(child);
            httpResponseEntity.setMessage("获取儿童信息成功");
        } catch ( Exception e ) {
            httpResponseEntity.setCode("-1");
            httpResponseEntity.setData(null);
            httpResponseEntity.setMessage("获取儿童信息时发生异常，请稍后重试");
        }
        return httpResponseEntity;
    }
    /**
     * 更新儿童信息
     * @return 更新是否成功
     */
    @PostMapping(value = "/updateChild", headers = "Accept=application/json")
    public HttpResponseEntity updateChild(@RequestBody Child child,@RequestHeader ("token")String token) {
        String id= JwtUtil.getUidFromToken(token);
        child.setUserId(id);
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            boolean result = childService.updateChildInfo(child);
            if ( result ) {
                httpResponseEntity.setCode("1");
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage("更新儿童信息成功");
            } else {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage("更新儿童信息失败");
            }
        } catch ( Exception e ) {
            httpResponseEntity.setCode("-1");
            httpResponseEntity.setData(null);
            httpResponseEntity.setMessage("更新儿童信息时发生异常，请稍后重试");
        }
        return httpResponseEntity;
    }
}

