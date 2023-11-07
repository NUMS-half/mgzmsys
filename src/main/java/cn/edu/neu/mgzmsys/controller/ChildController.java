package cn.edu.neu.mgzmsys.controller;


import cn.edu.neu.mgzmsys.common.utils.JwtUtil;
import cn.edu.neu.mgzmsys.entity.Child;
import cn.edu.neu.mgzmsys.entity.HttpResponseEntity;
import cn.edu.neu.mgzmsys.service.IChildService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

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
    public ResponseEntity<HttpResponseEntity> getChildById(@RequestHeader ("token")String token) throws ParseException {

        String id= JwtUtil.getUidFromToken(token);
        try{
            if ( id == null ){
                throw new NullPointerException();
            }
            cn.edu.neu.mgzmsys.entity.Child child = childService.selectChildInfo(id);
            if ( child != null ) {
                return new HttpResponseEntity().get(child).toResponseEntity();
            } else {
                return HttpResponseEntity.GET_FAIL.toResponseEntity();
            }
        } catch ( Exception e ) {
           return HttpResponseEntity.ERROR.toResponseEntity();
        }
    }
    /**
     * 更新儿童信息
     * @return 更新是否成功
     */
    @PostMapping(value = "/updateChild", headers = "Accept=application/json")
    public ResponseEntity<HttpResponseEntity> updateChild(@RequestBody Child child,@RequestHeader ("token")String token) throws ParseException {
        String id= JwtUtil.getUidFromToken(token);
        child.setUserId(id);
        try{
            boolean result = childService.updateChildInfo(child);
            if ( result ) {
                return HttpResponseEntity.UPDATE_SUCCESS.toResponseEntity();
            } else {
               return HttpResponseEntity.UPDATE_FAIL.toResponseEntity();
            }
        } catch ( Exception e ) {
            return HttpResponseEntity.ERROR.toResponseEntity();
        }
    }
}

