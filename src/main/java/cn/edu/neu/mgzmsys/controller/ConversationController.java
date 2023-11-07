package cn.edu.neu.mgzmsys.controller;


import cn.edu.neu.mgzmsys.common.utils.JwtUtil;
import cn.edu.neu.mgzmsys.entity.Child;
import cn.edu.neu.mgzmsys.entity.Conversation;
import cn.edu.neu.mgzmsys.entity.HttpResponseEntity;
import cn.edu.neu.mgzmsys.service.IChildService;
import cn.edu.neu.mgzmsys.service.IConversationService;
import cn.edu.neu.mgzmsys.service.IVolunteerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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
@RequestMapping("/conversation")
public class ConversationController {

    @Resource
    private IConversationService conversationService;

    @Resource
    private IChildService childService;

    @Resource
    private IVolunteerService volunteerService;

    @PostMapping(value = "/getId", headers = "Accept=application/json")
    public HttpResponseEntity getIdByTwoParticipantIds(@RequestBody String participants2id, @RequestHeader("token") String token) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        map.put("participantId1", JwtUtil.getUidFromToken(token));
        map.put("participantId2", participants2id);
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
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

    /**
     * 根据参与者id获取会话列表
     */
    @GetMapping(value = "/getByParticipantId", headers = "Accept=application/json")
    public HttpResponseEntity getByParticipantId(@RequestHeader("token") String token) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            String participantId = JwtUtil.getUidFromToken(token);
            List<Map<String, Object>> responseMapList = new ArrayList<>();
            List<Conversation> conversationList = conversationService.getByParticipantId(participantId);
            for ( Conversation conversation : conversationList ) {
                Map<String,Object> responseMap = new HashMap<>();
                responseMap.put("conversation", conversation);
                Child child = childService.selectChildInfo(conversation.getParticipantTwoId());
                if ( child != null ) {
                    responseMap.put("child", child);
                    responseMap.put("volunteer", null);
                } else {
                    responseMap.put("child", null);
                    responseMap.put("volunteer", volunteerService.selectVolunteerInfo(conversation.getParticipantTwoId()));
                }
                responseMapList.add(responseMap);
            }
            httpResponseEntity.setCode("1");
            httpResponseEntity.setData(responseMapList);
            httpResponseEntity.setMessage("获取成功");
        } catch ( Exception e ) {
            httpResponseEntity.setCode("-1");
            httpResponseEntity.setData(null);
            httpResponseEntity.setMessage("获取时发生异常，请稍后重试");
        }
        return httpResponseEntity;
    }
}

