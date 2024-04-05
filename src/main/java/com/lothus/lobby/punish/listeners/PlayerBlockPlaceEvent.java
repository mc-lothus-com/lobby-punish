package com.lothus.lobby.punish.listeners;

import com.lothus.lobby.punish.command.BuildCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerBlockPlaceEvent implements Listener {

    @EventHandler
    public void onBlock(BlockBreakEvent event) {
        if (!BuildCommand.getBuilders().contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlock(BlockPlaceEvent event) {
        if (!BuildCommand.getBuilders().contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
