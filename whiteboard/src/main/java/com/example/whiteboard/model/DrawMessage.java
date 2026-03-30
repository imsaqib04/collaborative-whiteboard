package com.example.whiteboard.model;

import lombok.Data;

@Data
public class DrawMessage {
    private String type;       // "draw", "clear", "cursor", "JOIN", ya "CHAT"
    private String senderName;
    private String color;
    private int width;

    private int oldX;
    private int oldY;
    private int newX;
    private int newY;

    private String text;       // Chat messages ke liye
}