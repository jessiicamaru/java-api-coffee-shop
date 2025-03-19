package com.example.coffeeshop.socket;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CoffeeWebSocketHandler extends TextWebSocketHandler {
    // Lưu danh sách session của từng khách hàng theo UID
    private static final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Lưu UID vào session khi client kết nối
        String uid = (String) session.getAttributes().get("uid");
        if (uid != null) {
            userSessions.put(uid, session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        // Xóa session khi khách hàng ngắt kết nối
        userSessions.values().remove(session);
    }

    // Gửi trạng thái đơn hàng đến đúng UID và OrderID
    public static void sendOrderStatus(String uid, String orderId, int status) throws IOException {
        WebSocketSession session = userSessions.get(uid);
        if (session != null && session.isOpen()) {
            String message = String.format("{\"orderId\": \"%s\", \"status\": %d}", orderId, status);
            session.sendMessage(new TextMessage(message));
        }
    }
}
