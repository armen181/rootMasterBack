package com.master.root.rootmaster.service.impl;

import com.master.root.rootmaster.exception.BadRequestException;
import com.master.root.rootmaster.models.Player;
import com.master.root.rootmaster.models.enums.PlayerLastResult;
import com.master.root.rootmaster.models.enums.PlayerState;
import com.master.root.rootmaster.service.PlayerService;
import com.master.root.rootmaster.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

import static com.master.root.rootmaster.models.enums.PlayerLastResult.NOT_ANSWERED;
import static com.master.root.rootmaster.models.enums.PlayerState.PREPARING;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final RoomService roomService;

    @Override
    public Player joinRoom(final String userName, final Integer token) {
        var player = new Player(
                UUID.randomUUID(),
                userName,
                PREPARING,
                NOT_ANSWERED,
                0
        );
        Set<Player> players = roomService.getRoom(token).players();
        if (players.contains(player)) {
            throw new BadRequestException("User with that name exists");
        }
        players.add(player);
        return player;
    }
}
