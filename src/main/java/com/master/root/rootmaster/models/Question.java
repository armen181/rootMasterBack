package com.master.root.rootmaster.models;

import java.util.Map;
import java.util.UUID;

public record Question (
        UUID id,
        int question,
        Map<Answer, Boolean> answers
) {
}
