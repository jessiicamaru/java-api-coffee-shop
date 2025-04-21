package com.example.coffeeshop.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CoffeeWebSocketHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(CoffeeWebSocketHandler.class);
    private static final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String uid = (String) session.getAttributes().get("uid");
        if (uid != null && !uid.equals("unknown")) {
            userSessions.put(uid, session);
            logger.info("WebSocket connected - UID: {}, Total sessions: {}", uid, userSessions.size());
        } else {
            logger.warn("WebSocket connected with invalid or missing UID: {}", uid);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String uid = (String) session.getAttributes().get("uid");
        if (uid != null && userSessions.remove(uid, session)) {
            logger.info("WebSocket disconnected - UID: {}, Status: {}, Remaining sessions: {}",
                    uid, status, userSessions.size());
        } else {
            logger.warn("Failed to remove session for UID: {} - Session not found or UID invalid", uid);
        }
    }

    public static void sendOrderStatus(String uid, String orderId, int status) throws IOException {
        if (uid == null) {
            logger.warn("Cannot send order status - UID is null for OrderId: {}, Status: {}", orderId, status);
            return;
        }
        WebSocketSession session = userSessions.get(uid);
        if (session != null && session.isOpen()) {
            String message = String.format("{\"orderId\": \"%s\", \"status\": %d}", orderId, status);
            session.sendMessage(new TextMessage(message));
            logger.info("Sent order status to UID: {} - OrderId: {}, Status: {}", uid, orderId, status);
        } else {
            logger.warn("Cannot send order status - No open session for UID: {}", uid);
        }
    }
}