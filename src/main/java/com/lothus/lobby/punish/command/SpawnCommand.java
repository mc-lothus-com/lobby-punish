package com.lothus.lobby.punish.command;

import com.lothus.lobby.punish.Instance;
import com.lothus.lobby.punish.utils.LocationUtils;
import com.mclothus.bukkit.commands.CommandBase;
import com.mclothus.core.Core;
import com.mclothus.core.player.LothPlayer;
import com.mclothus.core.player.group.rank.Rank;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends CommandBase {

    public SpawnCommand() {
        super(
                "spawn"
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
            if (!l.getGroup().containsPermission("lobby.spawn")) {
                player.sendMessage(NO_PERMISSION);
                return true;
            }
        }

        if (args.length == 0) {
            Instance.getInstance().getConfig().set("config.lobby", LocationUtils.getData(player.getLocation()));
            Instance.getInstance().saveConfig();
            player.sendMessage("§aO spawn foi definido com sucesso.");
        }
        return false;
    }
}
