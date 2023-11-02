package cn.edu.neu.mgzmsys.controller;

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

}

