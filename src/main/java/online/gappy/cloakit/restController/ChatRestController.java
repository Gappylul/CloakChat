package online.gappy.cloakit.restController;

import online.gappy.cloakit.model.ChatMessage;
import online.gappy.cloakit.service.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    private final ChatService chatService;

    public ChatRestController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/latest")
    public List<ChatMessage> getLatestMessages() {
        return chatService.getLatestMessages();
    }
}
