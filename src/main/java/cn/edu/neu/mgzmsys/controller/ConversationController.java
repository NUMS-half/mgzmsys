package cn.edu.neu.mgzmsys.controller;


import cn.edu.neu.mgzmsys.entity.Conversation;
import cn.edu.neu.mgzmsys.entity.HttpResponseEntity;
import cn.edu.neu.mgzmsys.service.IConversationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/conversation")
public class ConversationController {

    @Resource
    private IConversationService conversationService;

    @PostMapping(value = "/getId", headers = "Accept=application/json")
    public HttpResponseEntity getIdByTwoParticipantIds(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            if ( map == null ) {
                throw new NullPointerException();
            }
            String participantId1 = (String) map.get("participantId1");
            String participantId2 = (String) map.get("participantId2");
            Conversation conversation = conversationService.getByTwoParticipantIds(participantId1, participantId2);
            if ( conversation == null ) {
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage("该会话不存在");
            } else {
                httpResponseEntity.setCode("1");
                httpResponseEntity.setData(conversation.getConversationId());
                httpResponseEntity.setMessage("获取成功");
            }
        } catch ( Exception e ) {
            httpResponseEntity.setCode("-1");
            httpResponseEntity.setData(null);
            httpResponseEntity.setMessage("获取时发生异常，请稍后重试");
        }
        return httpResponseEntity;
    }
}

