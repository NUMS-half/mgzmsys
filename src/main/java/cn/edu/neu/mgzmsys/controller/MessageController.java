package cn.edu.neu.mgzmsys.controller;


import cn.edu.neu.mgzmsys.entity.HttpResponseEntity;
import cn.edu.neu.mgzmsys.service.IMessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
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

//    @Resource
//    private IMessageService messageService;
//
//    /**
//     * 发送消息
//     */
//    @MessageMapping(value = "/send")
//    public HttpResponseEntity handleSentMessage(@Payload Map<String, Object> message) {
//        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//        message.put("messageTime", new Date());
//        try {
//            if ( message == null ) {
//                throw new NullPointerException();
//            }
//            if ( messageService.handleSentMessage(message) ) {
//                httpResponseEntity.setCode("1");
//                httpResponseEntity.setData(null);
//                httpResponseEntity.setMessage("发送消息成功");
//            } else {
//                httpResponseEntity.setCode("-1");
//                httpResponseEntity.setData(null);
//                httpResponseEntity.setMessage("发送消息失败");
//            }
//        } catch ( Exception e ) {
//            httpResponseEntity.setCode("-1");
//            httpResponseEntity.setData(null);
//            httpResponseEntity.setMessage("发送消息时发生异常，请稍后重试");
//        }
//        return httpResponseEntity;
//    }

}

