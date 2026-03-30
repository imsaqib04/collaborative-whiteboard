package com.example.whiteboard.controller;

import com.example.whiteboard.model.DrawMessage;
import com.example.whiteboard.model.RoomSnapshot;
import com.example.whiteboard.repository.SnapshotRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Controller
public class WhiteboardController {

    private final SimpMessagingTemplate messagingTemplate;
    private final RedisTemplate<String, DrawMessage> redisTemplate;
    private final SnapshotRepository snapshotRepository; // Naya Repository
    private final ObjectMapper objectMapper; // Java Objects ko JSON me convert karne ke liye

    public WhiteboardController(SimpMessagingTemplate messagingTemplate,
                                RedisTemplate<String, DrawMessage> redisTemplate,
                                SnapshotRepository snapshotRepository) {
        this.messagingTemplate = messagingTemplate;
        this.redisTemplate = redisTemplate;
        this.snapshotRepository = snapshotRepository;
        this.objectMapper = new ObjectMapper();
    }

    @MessageMapping("/draw/{roomId}")
    public void broadcastDrawing(@DestinationVariable String roomId, DrawMessage message) {
        String redisKey = "room:" + roomId;

        if ("clear".equals(message.getType())) {
            redisTemplate.delete(redisKey);
        }
        else if ("draw".equals(message.getType()) || "CHAT".equals(message.getType())) {
            redisTemplate.opsForList().rightPush(redisKey, message);
        }
        // 🚀 NAYA LOGIC: Jab Teacher "Leave Room" dabaye
        else if ("ARCHIVE".equals(message.getType())) {
            try {
                // 1. Redis se poora data nikalo
                List<DrawMessage> history = redisTemplate.opsForList().range(redisKey, 0, -1);

                if (history != null && !history.isEmpty()) {
                    // 2. MySQL Snapshot Object banao
                    RoomSnapshot snapshot = new RoomSnapshot();
                    snapshot.setRoomId(roomId);
                    snapshot.setHostName(message.getSenderName());
                    snapshot.setSavedAt(LocalDateTime.now());

                    // 3. Drawing list ko JSON String mein convert karke save karo
                    String jsonCanvasData = objectMapper.writeValueAsString(history);
                    snapshot.setCanvasData(jsonCanvasData);

                    snapshotRepository.save(snapshot); // DB mein save

                    // 4. (Optional) Redis se delete kar do kyunki room band ho gaya
                    redisTemplate.delete(redisKey);
                }
            } catch (Exception e) {
                System.out.println("Snapshot save karne me error aayi: " + e.getMessage());
            }
        }

        messagingTemplate.convertAndSend("/topic/board/" + roomId, message);
    }

    @SubscribeMapping("/history/{roomId}")
    public List<DrawMessage> getHistory(@DestinationVariable String roomId) {
        String redisKey = "room:" + roomId;
        List<DrawMessage> history = redisTemplate.opsForList().range(redisKey, 0, -1);
        return history != null ? history : new ArrayList<>();
    }
}

