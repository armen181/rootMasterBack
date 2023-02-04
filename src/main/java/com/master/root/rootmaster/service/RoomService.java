package com.master.root.rootmaster.service;

import com.master.root.rootmaster.models.Room;

public interface RoomService {

    Room getRoom(Integer token);

    Room createRoom(String userName);
}
