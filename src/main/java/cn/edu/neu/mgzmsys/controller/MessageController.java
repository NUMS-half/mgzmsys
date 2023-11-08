package cn.edu.neu.mgzmsys.controller;


import cn.edu.neu.mgzmsys.entity.HttpResponseEntity;
import cn.edu.neu.mgzmsys.entity.Message;
import cn.edu.neu.mgzmsys.service.IMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private IMessageService messageService;

    /**
     * 查询conservation的消息记录
     */
    @GetMapping(value = "/history/{conversationId}", headers = "Accept=application/json")
    public ResponseEntity<HttpResponseEntity> getHistoryMessage(@PathVariable("conversationId") String conversationId) {
        try {
            if ( conversationId == null ) {
                throw new NullPointerException();
            }
            return new HttpResponseEntity().get(messageService.selectMessage(conversationId)).toResponseEntity();
        } catch ( Exception e ) {
            return HttpResponseEntity.ERROR.toResponseEntity();
        }
    }
}

