package com.master.root.rootmaster.models.dto;

public record AnswerDto(
        Integer questionId,
        Integer answerIndex,
        Integer roomId,
        String userName,
        Integer time
) {
}
