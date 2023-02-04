package com.master.root.rootmaster.service;

import com.master.root.rootmaster.models.Player;
import com.master.root.rootmaster.models.Question;

import java.util.Map;
import java.util.UUID;

public interface QuestionService {

    Question getNewQuestion(Integer token);

    Map<Player, Long> results(UUID questionId);

    void answer(UUID playerId, UUID questionId, UUID answerId);
}
