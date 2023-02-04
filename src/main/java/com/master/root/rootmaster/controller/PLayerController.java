package com.master.root.rootmaster.controller;

import com.master.root.rootmaster.models.Player;
import com.master.root.rootmaster.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PLayerController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<Set<Player>> getPlayersInRoom(final Integer token) {
        return ResponseEntity.ok(roomService.getRoom(token).players());
    }
}
