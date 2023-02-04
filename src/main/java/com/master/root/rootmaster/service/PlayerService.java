package com.master.root.rootmaster.service;

import com.master.root.rootmaster.models.Player;

public interface PlayerService {

    Player joinRoom(String userName, final String token);
}
