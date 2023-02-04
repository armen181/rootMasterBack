package com.master.root.rootmaster.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.master.root.rootmaster.exception.BadRequestException;
import com.master.root.rootmaster.models.Player;
import com.master.root.rootmaster.models.Question;
import com.master.root.rootmaster.models.Room;
import com.master.root.rootmaster.models.enums.PlayerState;
import com.master.root.rootmaster.service.RoomService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final static Random rnd = new Random();

    private final ObjectMapper objectMapper;

    private final CacheLoader<Integer, Room> loader = new CacheLoader<>() {
        @Override
        public Room load(@NonNull Integer token) {
            return new Room(token, new HashSet<>(), getQuestions());
        }
    };

    private Set<Question> getQuestions() {
        Set<Question> questions = new HashSet<>();
        try (var is = getClass().getClassLoader().getResourceAsStream("questions-1.json")) {
            questions.addAll(objectMapper.readValue(is, new TypeReference<>() {
            }));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return questions;
    }

    private final LoadingCache<Integer, Room> roomCache = CacheBuilder.newBuilder().build(loader);

    @Override
    public Room getRoom(final Integer token) {
        return Optional.ofNullable(roomCache.getIfPresent(token))
                .orElseThrow(() -> new BadRequestException("Room with token " + token + " not found"));
    }

    @Override
    public Room createRoom(String userName) {
        Room room = roomCache.getUnchecked(generateToken());
        var player = new Player(userName);
        player.setState(PlayerState.READY_TO_START);
        Set<Player> players = getRoom(room.token()).players();
        players.add(player);
        return room;
    }

    @Override
    public void changePlayerState(String id, Integer token, PlayerState newState) {
        getRoom(token).players().stream()
                .filter(p -> p.checkId(id))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Room with token " + token + " not found, cannot change player '" + id + "state to"))
                .setState(newState);
    }

    private Integer generateToken() {
        return 10000 + rnd.nextInt(90000);

    }

}
