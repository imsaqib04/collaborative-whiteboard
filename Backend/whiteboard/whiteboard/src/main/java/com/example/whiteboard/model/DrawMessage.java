package com.example.whiteboard.model;
import lombok.Data;

@Data // Lombok automatically getter/setter bana dega
public class DrawMessage {
    private String sender;
    private int x;
    private int y;
    private String color;
    private String type; // "draw" ya "stop"
}