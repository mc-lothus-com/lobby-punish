package com.lothus.lobby.punish.manager;

import com.lothus.lobby.punish.player.PunishedPlayer;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private HashMap<UUID, PunishedPlayer> players = new HashMap();

    public void load(PunishedPlayer player) {
        players.put(player.getUniqueId(), player);
    }

    public void unload(UUID uniqueId) {
        players.remove(uniqueId);
    }

    public PunishedPlayer get(UUID uniqueId) {
        return players.get(uniqueId);
    }
}
