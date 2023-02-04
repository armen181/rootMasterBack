package com.master.root.rootmaster.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.master.root.rootmaster.exception.BadRequestException;
import com.master.root.rootmaster.models.Room;
import com.master.root.rootmaster.service.RoomService;
import com.master.root.rootmaster.utils.RandomString;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;

@Service
public class RoomServiceImpl implements RoomService {

    private final CacheLoader<String, Room> loader = new CacheLoader<>() {
        @Override
        public Room load(@NonNull String token) {
            return new Room(token, new HashSet<>(), new HashSet<>());
        }
    };

    private final LoadingCache<String, Room> roomCache = CacheBuilder.newBuilder().build(loader);

    @Override
    public Room getRoom(final String token) {
        return Optional.ofNullable(roomCache.getIfPresent(token))
                .orElseThrow(() -> new BadRequestException("Room with token "+ token + " not found"));
    }

    @Override
    public Room createRoom() {
        return roomCache.getUnchecked(generateToken());
    }

    private String generateToken() {
        return RandomString.getAlphaNumericString(4);

    }

}
