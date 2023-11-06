package cn.edu.neu.mgzmsys.component;

import cn.edu.neu.mgzmsys.entity.Conversation;
import cn.edu.neu.mgzmsys.entity.Message;
import cn.edu.neu.mgzmsys.service.IConversationService;
import cn.edu.neu.mgzmsys.service.IMessageService;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket服务
 */

@ServerEndpoint(value = "/IMServer/{userId}")
@Component
public class WebSocketServer {

    private static IMessageService messageService;

    private static IConversationService conversationService;

    @Autowired
    public void setMessageService(IMessageService messageService) {
        WebSocketServer.messageService = messageService;
    }

    @Autowired
    public void setConversationService(IConversationService conversationService) {
        WebSocketServer.conversationService = conversationService;
    }

    // 连接超时时间(60分钟)
    private static final long SESSION_TIMEOUT = 60 * 60 * 1000;

    // 日志记录器
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    // 记录当前在线连接数(客户端个数)
    protected static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        sessionMap.put(userId, session);
        log.info("有新用户(userId:{})加入, 当前在线总人数为:{}", userId, sessionMap.size());

        // 启动定时任务，用于关闭过期的会话
        startSessionTimeoutTask(userId);

        // 从MQ中获取目标用户的消息
        List<Conversation> conversationList = conversationService.getByParticipantId(userId);
        for ( Conversation conversation : conversationList ) {
            Queue<Message> messageQueue = messageService.getSentMessages(conversation.getConversationId());
            while ( !messageQueue.isEmpty() ) {
                Message message = messageQueue.poll();
                // 处理消息，判断目标用户是否在线
                this.processMessage(message);
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        sessionMap.remove(userId);
        log.info("有1连接关闭，移除userId={}的用户session, 当前在线总人数为:{}", userId, sessionMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userId") String userId) {
        log.info("服务器收到用户(username:{})的消息:{}", userId, message);

        // 获取客户端发送过来的消息内容
        JSONObject obj = JSONUtil.parseObj(message);
        LocalDateTime localDateTime = LocalDateTime.now();
        Message sendMessage = new Message();
        sendMessage.setConversationId(obj.getStr("conversationId"));
        sendMessage.setPosterId(obj.getStr("posterId"));
        sendMessage.setReceiveId(obj.getStr("receiveId"));
        sendMessage.setMessageBody(obj.getStr("messageBody"));
        sendMessage.setMessageTime(localDateTime);
        sendMessage.setMessageType(obj.getInt("messageType"));
        sendMessage.setMessageStatus(0);

        // 保存消息
        if ( messageService.saveMessage(sendMessage) ) {
            log.info("消息保存数据库成功");
        } else {
            log.error("消息保存失败");
        }
        // 处理消息，判断目标用户是否在线
        this.processMessage(sendMessage);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 消息处理方法
     */
    private void processMessage(Message message) {
        // 解析消息
        String receiveUserId = message.getReceiveId();
        Session toSession = sessionMap.get(receiveUserId);

        if ( toSession != null ) {
            // 用户在线，推送消息给目标用户
            JSONObject jsonObject = new JSONObject(message);
            jsonObject.set("messageTime", message.getMessageTime().toString());
            sendMessage(jsonObject.toString(), toSession);
            log.info("发送给用户(userId:{}), 消息: {}", receiveUserId, jsonObject);
        } else {
            // 用户不在线，将消息存储到MQ中
            messageService.handleSentMessage(message);
            log.info("用户(userId:{})不在线，消息推送至MQ", receiveUserId);
        }
    }

    /**
     * 服务器发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务器给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch ( Exception e ) {
            log.error("服务器发送消息给客户端失败", e);
        }
    }

    /**
     * 启动定时任务，用于关闭过期的会话
     */
    private void startSessionTimeoutTask(String userId) {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() throws RuntimeException {
                Session session = sessionMap.get(userId);
                if ( session != null ) {
                    try {
                        session.close();
                    } catch ( IOException e ) {
                        throw new RuntimeException(e);
                    }
                    sessionMap.remove(userId);
                    log.info("用户(userId:{})的会话已过期并被关闭", userId);
                }
            }
        }, SESSION_TIMEOUT);
    }
}

