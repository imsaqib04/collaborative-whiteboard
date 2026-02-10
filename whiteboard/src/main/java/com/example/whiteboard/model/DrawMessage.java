package com.example.whiteboard.model;

import lombok.Data;

@Data
public class DrawMessage {
    private String type;   // "draw", "clear", "start"
    private String color;  // Hex code (e.g., "#FF0000")
    private int width;     // Brush thickness

    // Naya Coordinate System (Line banane ke liye)
    private int oldX;
    private int oldY;
    private int newX;
    private int newY;
}