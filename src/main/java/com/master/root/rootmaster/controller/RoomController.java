package com.master.root.rootmaster.controller;

import com.master.root.rootmaster.models.Player;
import com.master.root.rootmaster.models.Room;
import com.master.root.rootmaster.service.PlayerService;
import com.master.root.rootmaster.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<Room> createRoom(final String userName) {
        return ResponseEntity.ok(roomService.createRoom(userName));
    }

    @GetMapping
    public ResponseEntity<Room> getRoom(final Integer token) {
        return ResponseEntity.ok(roomService.getRoom(token));
    }

    @PostMapping("/join")
    public ResponseEntity<Player> joinRoom(
            final String userName,
            final Integer token) {
        return ResponseEntity.ok(playerService.joinRoom(userName, token));
    }
}
