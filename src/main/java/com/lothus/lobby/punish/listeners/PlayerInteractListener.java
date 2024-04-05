package com.lothus.lobby.punish.listeners;

import com.lothus.lobby.punish.menus.PunishMenu;
import net.minecraft.server.v1_8_R3.BlockBarrier;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();


        if (!event.hasBlock())return;
        if (!(event.getClickedBlock().getType() == Material.BARRIER))return;

        event.setCancelled(true);
        PunishMenu.open(player);
    }
}
