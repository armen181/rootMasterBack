package com.master.root.rootmaster.models;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record Question (
        int id,
        int answer,
        String question,
        List<String> options
) {
}
