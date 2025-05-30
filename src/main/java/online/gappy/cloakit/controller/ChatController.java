package online.gappy.cloakit.controller;

import online.gappy.cloakit.model.ChatMessage;
import online.gappy.cloakit.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    public ChatController(SimpMessagingTemplate messagingTemplate, ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }

    @GetMapping("/chat")
    public String chat(@AuthenticationPrincipal OidcUser user, Model model) {
        if (user != null) {
            model.addAttribute("username", user.getPreferredUsername());
            return "chat";
        } else {
            return "redirect:/";
        }
    }

    @MessageMapping("/chat")
    public void receiveMessage(
            @Payload ChatMessage chatMessage,
            Principal principal
    ) {
        String username = "Anonymous";

        if (principal instanceof Authentication auth && auth.getPrincipal() instanceof OidcUser oidcUser) {
            username = oidcUser.getPreferredUsername();
        }

        chatMessage.setSender(username);

        ChatMessage savedMessage = chatService.saveMessage(chatMessage);

        String content = savedMessage.getSender() + ": " + savedMessage.getContent();
        messagingTemplate.convertAndSend("/topic/chat/", content);
    }
}
