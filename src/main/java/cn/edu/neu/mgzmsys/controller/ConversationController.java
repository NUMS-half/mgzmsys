package cn.edu.neu.mgzmsys.controller;


import cn.edu.neu.mgzmsys.common.utils.JwtUtil;
import cn.edu.neu.mgzmsys.entity.Child;
import cn.edu.neu.mgzmsys.entity.Conversation;
import cn.edu.neu.mgzmsys.entity.HttpResponseEntity;
import cn.edu.neu.mgzmsys.entity.Volunteer;
import cn.edu.neu.mgzmsys.service.IChildService;
import cn.edu.neu.mgzmsys.service.IConversationService;
import cn.edu.neu.mgzmsys.service.IVolunteerService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<HttpResponseEntity> getIdByTwoParticipantIds(@RequestBody String participants2id, @RequestHeader("token") String token) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        map.put("participantId1", JwtUtil.getUidFromToken(token));
        map.put("participantId2", participants2id);
        try {
            String participantId1 = (String) map.get("participantId1");
            String participantId2 = (String) map.get("participantId2");
            Conversation conversation = conversationService.getByTwoParticipantIds(participantId1, participantId2);
            if ( conversation == null ) {
                return HttpResponseEntity.GET_FAIL.toResponseEntity();
            } else {
                return new HttpResponseEntity().get(conversation).toResponseEntity();
            }
        } catch ( Exception e ) {
            return HttpResponseEntity.ERROR.toResponseEntity();
        }
    }

    /**
     * 根据参与者id获取会话列表
     */
    @GetMapping(value = "/getByParticipantId", headers = "Accept=application/json")
    public ResponseEntity<HttpResponseEntity> getByParticipantId(@RequestHeader("token") String token) {
        try {
            String participantId = JwtUtil.getUidFromToken(token);
            List<Map<String, Object>> responseMapList = new ArrayList<>();
            List<Conversation> conversationList = conversationService.getByParticipantId(participantId);
            for ( Conversation conversation : conversationList ) {
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("conversation", conversation);
                Child child = null;
                if (conversation.getParticipantOneId().equals(participantId)) {
                    child = childService.selectChildInfo(conversation.getParticipantTwoId());
                } else {
                    child = childService.selectChildInfo(conversation.getParticipantOneId());
                }
                if ( child != null ) {
                    responseMap.put("child", child);
                    responseMap.put("volunteer", null);
                } else {
                    responseMap.put("child", null);
                    Volunteer volunteer = null;
                    if (conversation.getParticipantOneId().equals(participantId)) {
                        volunteer = volunteerService.selectVolunteerInfo(conversation.getParticipantTwoId());
                    } else {
                        volunteer = volunteerService.selectVolunteerInfo(conversation.getParticipantOneId());
                    }
                    responseMap.put("volunteer", volunteer);
                }
                responseMapList.add(responseMap);
            }
            return new HttpResponseEntity().get(responseMapList).toResponseEntity();
        } catch ( Exception e ) {
            return HttpResponseEntity.ERROR.toResponseEntity();
        }
    }
}

