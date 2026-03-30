package com.example.whiteboard.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class RoomSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;
    private String hostName;

    private LocalDateTime savedAt;

    @Column(columnDefinition = "LONGTEXT")
    private String canvasData; // Poori drawing JSON format mein yahan save hogi
}