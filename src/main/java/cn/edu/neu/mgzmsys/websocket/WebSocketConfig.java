package cn.edu.neu.mgzmsys.websocket;

import cn.edu.neu.mgzmsys.websocket.MyWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myNewWebSocketHandler(), "/ws").setAllowedOrigins("*");
    }

    @Bean
    public MyWebSocketHandler myNewWebSocketHandler() {
        return new MyWebSocketHandler();
    }

}
