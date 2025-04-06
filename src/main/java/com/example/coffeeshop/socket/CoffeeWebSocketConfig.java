package com.example.coffeeshop.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocket
public class CoffeeWebSocketConfig implements WebSocketConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(CoffeeWebSocketConfig.class);

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new CoffeeWebSocketHandler(), "/order-status")
                .setAllowedOrigins("*")
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(
                            ServerHttpRequest request,
                            ServerHttpResponse response,
                            WebSocketHandler wsHandler,
                            Map<String, Object> attributes) throws Exception {
                        String query = request.getURI().getQuery();
                        String uid = null;

                        if (query != null && !query.isEmpty()) {
                            uid = query.replace("uid=", "");
                            logger.info("WebSocket handshake - UID extracted: {}", uid);
                        } else {
                            logger.warn("WebSocket handshake - No query string found in URI: {}", request.getURI());
                            uid = "unknown"; // Giá trị mặc định nếu không có UID
                        }

                        attributes.put("uid", uid);
                        return true; // Tiếp tục handshake
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                               WebSocketHandler wsHandler, Exception exception) {
                        if (exception != null) {
                            logger.error("WebSocket handshake failed: {}", exception.getMessage());
                        }
                    }
                });
    }
}