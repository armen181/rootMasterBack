package com.master.root.rootmaster.controller;

import com.master.root.rootmaster.exception.BadRequestException;
import com.master.root.rootmaster.models.Player;
import com.master.root.rootmaster.models.enums.PlayerState;
import com.master.root.rootmaster.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<Set<Player>> getPlayersInRoom(final Integer token) {
        return ResponseEntity.ok(roomService.getRoom(token).players());
    }

    @PostMapping("/ready")
    public void ready(String userName, Integer token) {
        roomService.getRoom(token)
                .players()
                .stream().filter(player -> player.getUserName().equals(userName))
                .findFirst()
                .map(player -> {
                    player.setState(PlayerState.READY_TO_START);
                    return player;
                })
                .orElseThrow(() -> new BadRequestException("player or or room not found"));
    }

    @PostMapping("/preparing")
    public void preparing(String userName, Integer token) {
        roomService.getRoom(token)
                .players()
                .stream().filter(player -> player.getUserName().equals(userName))
                .findFirst()
                .map(player -> {
                    player.setState(PlayerState.PREPARING);
                    return player;
                })
                .orElseThrow(() -> new BadRequestException("player or or room not found"));
    }

    @PostMapping("/leave")
    public void siktir(String userName, Integer roomId) {
        roomService.getRoom(roomId)
                .players()
                .removeIf(player -> player.getUserName().equals(userName));
    }
}
