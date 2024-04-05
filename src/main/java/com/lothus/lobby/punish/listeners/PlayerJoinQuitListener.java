package com.lothus.lobby.punish.listeners;

import com.lothus.lobby.punish.Instance;
import com.lothus.lobby.punish.Platform;
import com.lothus.lobby.punish.player.PunishedPlayer;
import com.lothus.lobby.punish.utils.LocationUtils;
import com.mclothus.core.Core;
import com.mclothus.core.player.LothPlayer;
import com.mclothus.core.punish.type.PunishType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.github.paperspigot.Title;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PunishedPlayer punishedPlayer = new PunishedPlayer(player.getUniqueId(), null);
        LothPlayer lothPlayer = Core.getPlayerController().get(player.getUniqueId());

        lothPlayer.getPunishes().values().forEach(info -> {
            if (!info.isExpired()) {
                if (info.getType() == PunishType.BAN || info.getType() == PunishType.TEMP_BAN) {
                    punishedPlayer.setPunishesInfo(info);
                }
            }
        });

        if (punishedPlayer.getPunishesInfo() == null) {
            player.kickPlayer("§cNosso servidor de punição está indisponível para a sua conta.");
            return;
        }

        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(LocationUtils.getLocation(Instance.getInstance().getConfig().getString("config.lobby")));
        Platform.getPlayerManager().load(punishedPlayer);


        player.setHealth(20.0D);
        player.setFoodLevel(20);

        player.getInventory().clear();

        player.sendTitle(new Title(TextComponent.fromLegacyText("§c§lBANIDO!"), TextComponent.fromLegacyText("§eA sua conta está suspensa."), 20, 60, 20));
        TextComponent textComponent = new TextComponent("\n§eVocê está §bbanido§e dos nossos servidores.\n§eA sua punição" + (punishedPlayer.getPunishesInfo().isUnBan() ? "" : " §cnão") + " §epossui perdão.\n");
        player.sendMessage(textComponent);
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onQUit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Platform.getPlayerManager().unload(player.getUniqueId());
    }
}
