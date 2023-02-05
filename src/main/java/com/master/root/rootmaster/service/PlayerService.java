package com.master.root.rootmaster.service;

import com.master.root.rootmaster.models.Room;

public interface PlayerService {

    Room joinRoom(String userName, final Integer token);
}
