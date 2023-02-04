package com.master.root.rootmaster.models;

import com.master.root.rootmaster.models.enums.PlayerLastResult;
import com.master.root.rootmaster.models.enums.PlayerState;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

import static com.master.root.rootmaster.models.enums.PlayerLastResult.NOT_ANSWERED;
import static com.master.root.rootmaster.models.enums.PlayerState.PREPARING;

@AllArgsConstructor
@Getter
public class Player {
    private UUID id;
    private String userName;
    private PlayerState state;
    private PlayerLastResult lastResult;
    private int score;


    public Player(String userName) {
        this(UUID.randomUUID(), userName, PREPARING, NOT_ANSWERED, 0);
    }

    public Player changeState(PlayerState newState) {
        return new Player(this.id, this.userName, newState, this.lastResult, this.score);
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public void setLastResult(PlayerLastResult lastResult) {
        this.lastResult = lastResult;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean checkId(String requestId) {
        return id.equals(UUID.fromString(requestId));
    }

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
