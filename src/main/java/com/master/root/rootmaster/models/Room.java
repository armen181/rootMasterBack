package com.master.root.rootmaster.models;

import java.util.Set;

public record Room(
        Integer token,
        Set<Player> players,
        Set<Question> questions
) {
}
