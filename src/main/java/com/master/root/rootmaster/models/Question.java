package com.master.root.rootmaster.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Question(
        @JsonProperty("id")
        Integer id,
        @JsonProperty("question")
        String question,
        @JsonProperty("answer_index")
        Integer answerIndex,
        @JsonProperty("answers")
        List<String> answers
) {
}
