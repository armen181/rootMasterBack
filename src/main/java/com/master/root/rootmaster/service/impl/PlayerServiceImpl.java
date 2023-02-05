package com.master.root.rootmaster.service.impl;

import com.master.root.rootmaster.exception.BadRequestException;
import com.master.root.rootmaster.models.Player;
import com.master.root.rootmaster.models.Room;
import com.master.root.rootmaster.service.PlayerService;
import com.master.root.rootmaster.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final RoomService roomService;

    @Override
    public Room joinRoom(final String userName, final Integer token) {
        var player = new Player(userName);
        Room room = roomService.getRoom(token);
        Set<Player> players = room.players();
        if (players.contains(player)) {
            throw new BadRequestException("User with that name exists");
        }
        players.add(player);
        return room;
    }
}
