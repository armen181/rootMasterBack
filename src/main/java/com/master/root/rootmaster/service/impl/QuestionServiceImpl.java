package com.master.root.rootmaster.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.master.root.rootmaster.models.Player;
import com.master.root.rootmaster.models.Question;
import com.master.root.rootmaster.service.QuestionService;
import com.master.root.rootmaster.service.RoomService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final RoomService roomService;

    private final Random random = new Random();

    private final CacheLoader<Integer, Question> loader = new CacheLoader<>() {
        @Override
        public Question load(@NonNull Integer token) {

            return null;
        }
    };

    private final LoadingCache<Integer, Question> roomCache = CacheBuilder.newBuilder().build(loader);

    @Override
    public Map<Player, Long> results(final UUID questionId) {
        return null;
    }

    @Override
    public void answer(final UUID playerId, final UUID questionId, final UUID answerId) {

    }
}
