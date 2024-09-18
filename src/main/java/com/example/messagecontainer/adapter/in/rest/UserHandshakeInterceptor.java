//package com.example.messagecontainer.adapter.in.rest;
//
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import java.util.Map;
//
//@Component
//public class UserHandshakeInterceptor implements HandshakeInterceptor {
//
//    private boolean toggle = true; // Zmienna do przełączania między użytkownikami
//
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        // Na sztywno przypisanie dwóch użytkowników
//        String userId;
//
//        if (toggle) {
//            userId = "user1";  // Przypisanie pierwszego użytkownika
//        } else {
//            userId = "user2";  // Przypisanie drugiego użytkownika
//        }
//
//        // Zmieniaj wartość toggle, aby przełączać użytkowników przy kolejnych handshake'ach
//        toggle = !toggle;
//
//        // Przypisanie wybranego użytkownika do sesji WebSocket
//        attributes.put("user", userId);
//
//        return true; // Kontynuowanie procesu handshake
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                               WebSocketHandler wsHandler, Exception exception) {
//        // Nie trzeba nic robić po handshake
//    }
//}