//package com.example.messagecontainer.adapter.in.rest;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//import java.security.Principal;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Controller
//public class WebSocketController {
//
//
//    List<Message> messages = new ArrayList<>();
//
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    @MessageMapping("/chat")
//    public void send(@Payload Message message, Principal principal) throws Exception {
//
//        System.out.println("Message sent: " + message.text()+" "+message.from()+" "+message.to());
//        String time = new SimpleDateFormat("HH:mm").format(new Date());
//        OutputMessage outputMessage = new OutputMessage(message.from(), message.text(), time);
//        System.out.println("Message sent: " + outputMessage.getText());
//
//        // Send the message to the specific user
//        messagingTemplate.convertAndSend("/topic/messages/"+message.to(), new Message(message.from(), message.text(), "Q"));
//        messagingTemplate.convertAndSendToUser("user1", "/topic/messages/"+message.to(), outputMessage);
//        sendMessageToUser("a", "Hello");
//      //  messagingTemplate.convertAndSendToUser(message.from(), "/topic/messages", outputMessage);
//    }
//
//
//
//
//
//    public void sendMessageToUser(String username, String messageContent) {
//        // Stworzenie wiadomości do wysłania
//        OutputMessage outputMessage = new OutputMessage("System", messageContent, "Q");
//
//        // Wysłanie wiadomości do konkretnego użytkownika
//        messagingTemplate.convertAndSendToUser(username, "/queue/messages", outputMessage);
//    }
//
//
//
//}