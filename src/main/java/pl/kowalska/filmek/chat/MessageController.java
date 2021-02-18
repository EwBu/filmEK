package pl.kowalska.filmek.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message get(Message chatMessage) {
        return chatMessage;
    }

}
