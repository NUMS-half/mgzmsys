package cn.edu.neu.mgzmsys.component;

import cn.edu.neu.mgzmsys.common.utils.JwtUtil;
import cn.edu.neu.mgzmsys.controller.FileController;
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
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket服务
 */

@Component
@ServerEndpoint(value = "/IMServer/{token}")
public class WebSocketServer {

    // 连接超时时间(60分钟)
    private static final long SESSION_TIMEOUT = 60 * 60 * 1000;

    // 控制台日志记录器
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    // 记录当前用户个数
    protected static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

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

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws ParseException, IOException {

        String userId = JwtUtil.getUidFromToken(token);

        // 将新登录的用户session以及id加入sessionMap
        sessionMap.put(userId, session);
        log.info("有新用户(userId:{})加入, 当前在线总人数为:{}", userId, sessionMap.size());

        // 设置session的过期时间
        startSessionTimeoutTask(userId);

        // 从MQ中获取目标用户的消息
        List<Conversation> conversationList = conversationService.getByParticipantId(userId);
        for ( Conversation conversation : conversationList ) {
            Queue<Message> messageQueue = messageService.getSentMessages(conversation.getConversationId());
            while ( !messageQueue.isEmpty() ) {
                Message message = messageQueue.poll();
                // 处理消息，判断目标用户是否在线
                this.processMessage(session, message);
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("token") String token) throws ParseException {
        String userId = JwtUtil.getUidFromToken(token);
        sessionMap.remove(userId);
        log.info("userId={}的用户离线, 当前在线总人数为:{}", userId, sessionMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("token") String token) throws ParseException, IOException {
        String userId = JwtUtil.getUidFromToken(token);
        log.info("服务器收到用户(username:{})的消息:{}", userId, message);

        // 获取客户端发送过来的消息内容
        JSONObject obj = JSONUtil.parseObj(message);

        // 创建Message对象,并将客户端发送的消息进行转换
        Message sendMessage = new Message();
        sendMessage.setConversationId(obj.getStr("conversationId"));
        sendMessage.setPosterId(userId);
        sendMessage.setReceiveId(obj.getStr("receiveId"));
        sendMessage.setMessageBody(obj.getStr("messageBody"));
        sendMessage.setMessageTime(LocalDateTime.now());
        sendMessage.setMessageType(obj.getInt("messageType"));
        sendMessage.setMessageStatus(obj.getInt("messageStatus"));

        // 处理该消息
        this.processMessage(session, sendMessage);
    }

    /**
     * 发生错误时调用的方法
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 消息处调用的方法
     */
    private void processMessage(Session session, Message message) throws IOException {

        // 消息状态码：0-已发送的消息，1-离线消息，2-已接收的消息

        // 解析消息(获取收信人id与session)
        String receiveUserId = message.getReceiveId();
        Session toSession = sessionMap.get(receiveUserId);

        // 将消息转换为json格式
        JSONObject jsonObject = new JSONObject(message);
        switch ( message.getMessageType() ) {
            // 文字消息
            case 0:
                if ( toSession != null ) {
                    // 用户在线，消息保存到数据库，并推送给目标用户
                    if ( message.getMessageStatus() == 1 ) {
                        message.setMessageStatus(2);
                        messageService.updateById(message);
                        log.info("更新的消息ID：" + message.getMessageId());
                        jsonObject.set("responseType", "1"); // 响应信息类型：1-文字消息
                        jsonObject.set("messageId", message.getMessageId());
                        jsonObject.set("messageStatus", message.getMessageStatus());
                        jsonObject.set("messageTime", message.getMessageTime().toString());
                        sendMessage(jsonObject.toString(), toSession);
                        log.info("发送给用户(userId:{}), 文字消息: {}", receiveUserId, jsonObject);
                    } else if ( message.getMessageStatus() == 0 ) {
                        message.setMessageStatus(2);
                        if ( messageService.saveMessage(message) ) {
                            log.info("新保存的消息ID：" + message.getMessageId());
                            jsonObject.set("responseType", "1"); // 响应信息类型：1-文字消息
                            jsonObject.set("messageId", message.getMessageId());
                            jsonObject.set("messageStatus", 2);
                            jsonObject.set("messageTime", message.getMessageTime().toString());
                            sendMessage(jsonObject.toString(), toSession);
                            log.info("发送给用户(userId:{}), 文字消息: {}", receiveUserId, jsonObject);
                        } else {
                            log.error("消息保存失败");
                        }
                    }
                } else {
                    // 用户不在线，将消息存储到MQ中
                    if ( message.getMessageStatus() == 0 ) {
                        message.setMessageStatus(1);
                        messageService.saveMessage(message);
                    }
                    messageService.handleSentMessage(message);
                    log.info("用户(userId:{})不在线，消息推送至MQ", receiveUserId);
                }
                break;
            case 1:
                if ( toSession != null ) {
                    // 用户在线，消息保存到数据库，并推送给目标用户
                    if ( message.getMessageStatus() == 1 ) {
                        message.setMessageStatus(2);
                        messageService.updateById(message);
                        log.info("更新的消息ID：" + message.getMessageId());
                        jsonObject.set("responseType", "2"); // 响应信息类型：2-文件消息
                        jsonObject.set("messageId", message.getMessageId());
                        jsonObject.set("messageStatus", message.getMessageStatus());
                        jsonObject.set("messageTime", message.getMessageTime().toString());
                        jsonObject.set("messageBody", FileController.FILE_UPLOAD_DIRECTORY +
                                message.getMessageId() + "/" + message.getMessageBody());
                        sendMessage(jsonObject.toString(), toSession);
                        log.info("发送给用户(userId:{}), 文件消息: {}", receiveUserId, jsonObject);
                    } else if ( message.getMessageStatus() == 0 ) {
                        if ( messageService.saveMessage(message) ) {
                            log.info("新保存的消息ID：" + message.getMessageId());
                            jsonObject.set("responseType", "2"); // 响应信息类型：2-文件消息
                            jsonObject.set("messageId", message.getMessageId());
                            jsonObject.set("messageStatus", 2);
                            jsonObject.set("messageTime", message.getMessageTime().toString());
                            jsonObject.set("messageBody", "http://localhost:8080/file/download?" +
                                    FileController.FILE_UPLOAD_DIRECTORY +
                                    message.getMessageId() + "/" + message.getMessageBody());
                            sendMessage(jsonObject.toString(), toSession); // 发送给目标用户
                            log.info("发送给用户(userId:{}), 文件消息: {}", receiveUserId, jsonObject);
                        } else {
                            log.error("消息保存失败");
                        }
                    }
                } else {
                    // 用户不在线，将消息存储到MQ中
                    if ( message.getMessageStatus() == 0 ) {
                        message.setMessageStatus(1);
                        messageService.saveMessage(message);
                    }
                    messageService.handleSentMessage(message);
                    log.info("用户(userId:{})不在线，消息推送至MQ", receiveUserId);
                }
            case 2:
                // 视频通话消息

                break;
            default:
                log.error("传入的消息类型错误");
                break;
        }

    }

    /**
     * 服务器发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务器给客户端{}发送消息{}", toSession.getId(), message);
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

