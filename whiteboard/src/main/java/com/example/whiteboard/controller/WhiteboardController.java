package com.example.whiteboard.controller;

import com.example.whiteboard.model.DrawMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WhiteboardController {

    @MessageMapping("/draw") // Frontend yahan data bhejega
    @SendTo("/topic/board")  // Server yahan sabko forward karega
    public DrawMessage broadcastDrawing(DrawMessage message) {
        return message; // Jo aaya wahi sabko bhej do
    }
}