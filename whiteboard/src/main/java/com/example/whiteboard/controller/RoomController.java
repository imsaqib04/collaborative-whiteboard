package com.example.whiteboard.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    // Ye set active rooms ka record rakhega
    private static Set<String> activeRooms = ConcurrentHashMap.newKeySet();

    // Jab Host room banayega, tab ye call hoga
    @PostMapping("/create/{roomCode}")
    public ResponseEntity<String> createRoom(@PathVariable String roomCode) {
        activeRooms.add(roomCode);
        return ResponseEntity.ok("Room Created");
    }

    // Jab Guest join karega, tab ye check karega
    @GetMapping("/check/{roomCode}")
    public ResponseEntity<Boolean> checkRoom(@PathVariable String roomCode) {
        boolean exists = activeRooms.contains(roomCode);
        return ResponseEntity.ok(exists);
    }

    // Jab room end hoga, tab ye call hoga (Optional but good practice)
    @DeleteMapping("/end/{roomCode}")
    public ResponseEntity<String> endRoom(@PathVariable String roomCode) {
        activeRooms.remove(roomCode);
        return ResponseEntity.ok("Room Ended");
    }
}