package com.master.root.rootmaster.models;

import com.master.root.rootmaster.models.enums.PlayerLastResult;
import com.master.root.rootmaster.models.enums.PlayerState;

import java.util.Objects;
import java.util.UUID;

public record Player(
        UUID id,
        String userName,
        PlayerState state,
        PlayerLastResult lastResult,
        int score

) {

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Player player = (Player) o;

        return Objects.equals(userName, player.userName);
    }

    @Override
    public int hashCode() {
        return userName != null ? userName.hashCode() : 0;
    }
}
