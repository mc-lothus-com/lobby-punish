package com.lothus.lobby.punish.menus;

import com.lothus.lobby.punish.Platform;
import com.lothus.lobby.punish.player.PunishedPlayer;
import com.mclothus.bukkit.utils.items.ItemCreator;
import com.mclothus.core.Core;
import com.mclothus.core.player.LothPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PunishMenu implements Listener {

    public static void open(Player player) {
        LothPlayer lothPlayer = Core.getPlayerController().get(player.getUniqueId());
        PunishedPlayer punishedPlayer = Platform.getPlayerManager().get(player.getUniqueId());

        Inventory inventory = Bukkit.createInventory(null, 9*3, "Informações");

        inventory.setItem(
                13, new ItemCreator(Material.SKULL_ITEM, "§aInformações da Punição")
                        .setLore(
                                "",
                                "§fUnban disponível: " + (punishedPlayer.getPunishesInfo().isUnBan() ? "§aSim" : "§cNão"),
                                "",
                                "§fIdentificação: §e" + punishedPlayer.getPunishesInfo().getId(),
                                "§fAutor: §7" + punishedPlayer.getPunishesInfo().getAuthor(),
                                "§fMotivo: §7" + punishedPlayer.getPunishesInfo().getReason().getDisplay(),
                                "§fEvidência: §7" + punishedPlayer.getPunishesInfo().getEvidence(),
                                "§fFinaliza em: §7" + (punishedPlayer.getPunishesInfo().getExpires() == -1L ? "Nunca" : new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(punishedPlayer.getPunishesInfo().getExpires())),
                                "",
                                "§fStatus: " + (punishedPlayer.getPunishesInfo().isExpired() ? "§aExpirado" : "§cAtivo"),
                                "§fCusto: §612.000 cash",
                                "",
                                (lothPlayer.getCash() < 12000 ? "§cVocê não possui cash o suficiente." : punishedPlayer.getPunishesInfo().isUnBan() ? "§eClique para comprar." : "§cA sua punição não pode ser perdoada.")
                        )
                        .setAmount(1)
                        .setId(3)
                        .withSkullOwner(player.getName()).build()
        );

        player.openInventory(inventory);
    }

    private HashMap<UUID, Long> cooldown = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ItemStack itemStack = event.getCurrentItem();

        if (inventory == null)return;
        if (itemStack.getType() == Material.AIR)return;

        if (!inventory.getName().equalsIgnoreCase("Informações"))return;

        event.setCancelled(true);

        LothPlayer lothPlayer = Core.getPlayerController().get(player.getUniqueId());
        PunishedPlayer punishedPlayer = Platform.getPlayerManager().get(player.getUniqueId());

        if (event.getRawSlot() == 13) {

            if (!punishedPlayer.getPunishesInfo().isUnBan()) {
                player.sendMessage("§cA sua punição não pode ser perdoada.");
                return;
            }

            if (punishedPlayer.getPunishesInfo().isExpired()) {
                player.sendMessage("§eA punição §b§n" + punishedPlayer.getPunishesInfo().getReason().getDisplay() + "§e está expirada.");
                return;
            }

            if (lothPlayer.getCash() < 12000) {
                player.sendMessage("§cVocê não possui cash o suficiente.");
                return;
            }

            if (cooldown.get(player.getUniqueId()) != null && cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                return;
            }

            cooldown.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5));

            lothPlayer.setCash(lothPlayer.getCash() - 12000);
            punishedPlayer.getPunishesInfo().setExpired(true);
            lothPlayer.getPunishes().replace(punishedPlayer.getPunishesInfo().getId(), punishedPlayer.getPunishesInfo());
            player.sendMessage("§eVocê comprou §bunban§e.");
            Core.getDataPlayer().update(lothPlayer);
            player.closeInventory();
            player.kickPlayer("");
        }
    }
}
