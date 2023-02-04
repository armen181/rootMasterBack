package com.master.root.rootmaster.service;

import com.master.root.rootmaster.models.Room;
import com.master.root.rootmaster.models.enums.PlayerState;


public interface RoomService {

    Room getRoom(Integer token);

    Room createRoom(String userName);

    void changePlayerState(final String id, final Integer token, PlayerState newState);
}
