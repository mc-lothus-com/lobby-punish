package com.lothus.lobby.punish.command;

import com.lothus.lobby.punish.Instance;
import com.lothus.lobby.punish.utils.LocationUtils;
import com.mclothus.bukkit.commands.CommandBase;
import com.mclothus.core.Core;
import com.mclothus.core.player.LothPlayer;
import com.mclothus.core.player.group.rank.Rank;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BuildCommand extends CommandBase {

    @Getter
    private static List<UUID> builders = new ArrayList<>();

    public BuildCommand() {
        super(
                "build"
        );
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        LothPlayer l = Core.getPlayerController().get(player.getUniqueId());

        if (!(l.getGroup().getRank().ordinal() <= Rank.CEO.ordinal())) {
            if (!l.getGroup().containsPermission("lobby.build")) {
                player.sendMessage(NO_PERMISSION);
                return true;
            }
        }

        if (args.length == 0) {
            if (builders.contains(player.getUniqueId())) {
                builders.remove(player.getUniqueId());
                player.sendMessage("§cVocê saiu do modo construtor.");
                player.setGameMode(GameMode.ADVENTURE);
            } else {
                builders.add(player.getUniqueId());
                player.sendMessage("§aVocê entrou no modo construtor.");
                player.setGameMode(GameMode.CREATIVE);
            }
        }
        return false;
    }
}
