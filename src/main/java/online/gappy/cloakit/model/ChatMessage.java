package online.gappy.cloakit.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;
    private String content;
    private Instant timestamp;

    @PrePersist
    public void setTimestamp() {
        this.timestamp = Instant.now();
    }
}
