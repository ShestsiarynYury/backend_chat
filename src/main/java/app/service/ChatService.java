package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.dto.ChatMessage;
import app.model.User;

@Service
public class ChatService {
    @Autowired private UserService userService;
    @Autowired private app.service.MessageService messageService;

    @Transactional(propagation = Propagation.REQUIRED)
    public ChatMessage pushToChat(ChatMessage chatMessage) {
        User sender = this.userService.findByLogin(chatMessage.getSender());

        return chatMessage;
    }
}
