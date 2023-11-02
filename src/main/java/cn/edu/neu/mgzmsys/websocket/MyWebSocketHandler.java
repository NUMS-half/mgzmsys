package cn.edu.neu.mgzmsys.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private static final List<WebSocketSession> sessions = new ArrayList<>();
    private static final List<String> messageHistory = new ArrayList<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);

        // 发送历史消息给新连接的用户
        for (String message : messageHistory) {
            session.sendMessage(new TextMessage(message));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    // MyWebSocketHandler.java

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        String[] parts = payload.split(":");
        String senderUsername = parts[0].trim();
        String messageContent = parts[1].trim();

        sendMessageToAllClients(senderUsername, messageContent);
        messageHistory.add(senderUsername + ": " + messageContent);
    }

    private void sendMessageToAllClients(String senderUsername, String message) throws IOException {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(senderUsername + ": " + message));
            }
        }
    }

}
