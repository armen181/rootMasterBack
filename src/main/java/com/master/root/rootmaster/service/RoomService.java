package com.master.root.rootmaster.service;

import com.master.root.rootmaster.models.Room;

public interface RoomService {

    Room getRoom(String token);

    Room createRoom();
}
