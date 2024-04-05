package com.lothus.lobby.punish.player;

import com.mclothus.core.punish.PunishesInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class PunishedPlayer {

    private UUID uniqueId;

    private PunishesInfo punishesInfo;

    public PunishedPlayer(UUID uniqueId, PunishesInfo punishesInfo) {
        this.uniqueId = uniqueId;
        this.punishesInfo = punishesInfo;
    }
}
