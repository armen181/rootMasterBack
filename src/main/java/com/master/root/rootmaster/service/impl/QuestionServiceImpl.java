package com.master.root.rootmaster.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.master.root.rootmaster.models.Answer;
import com.master.root.rootmaster.models.Player;
import com.master.root.rootmaster.models.Question;
import com.master.root.rootmaster.models.Room;
import com.master.root.rootmaster.service.QuestionService;
import com.master.root.rootmaster.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final RoomService roomService;

    private final Random random = new Random();

    private final CacheLoader<Integer, Question> loader = new CacheLoader<>() {
        @Override
        public Question load(@NonNull Integer token) {
            var newQuestion = getNewQuestion(token);
            roomService.getRoom(token).questions().add(newQuestion);
            return newQuestion;
        }
    };

    private final LoadingCache<Integer, Question> roomCache = CacheBuilder.newBuilder().build(loader);


    @Override
    public Question getNewQuestion(final Integer token) {
        var origin = random.nextInt(100);
        int number = random.nextInt(origin-3, origin+3);
        int questionNumber = (int)Math.pow(number,2);

        var answers = Map.of(
                new Answer(UUID.randomUUID(), number),
                true,
                new Answer(UUID.randomUUID(), number + random.nextInt(3)),
                false,
                new Answer(UUID.randomUUID(), number + random.nextInt(3)),
                false,
                new Answer(UUID.randomUUID(), number + random.nextInt(3)),
                false
        );
        return new Question(
                1,
                questionNumber,
                "questionNumber",
                answers.keySet().stream().map(answer -> String.valueOf(answer.answer())).collect(Collectors.toList())

        );
    }

    @Override
    public Map<Player, Long> results(final UUID questionId) {
        return null;
    }

    @Override
    public void answer(final UUID playerId, final UUID questionId, final UUID answerId) {

    }
}
