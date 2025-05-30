package online.gappy.cloakit.service;

import online.gappy.cloakit.model.ChatMessage;
import online.gappy.cloakit.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatMessage saveMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getLatestMessages() {
        return chatMessageRepository.findTop50ByOrderByTimestampDesc();
    }
}
