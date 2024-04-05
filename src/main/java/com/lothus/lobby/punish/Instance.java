package com.lothus.lobby.punish;

import com.mclothus.bukkit.commands.loader.BukkitCommandLoader;
import com.mclothus.bukkit.listeners.loader.ListenerLoader;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

public class Instance extends JavaPlugin {

    @Getter @Setter
    private static Instance instance;

    @Override
    public void onLoad() {
        setInstance(this);
    }

    @Override
    public void onEnable() {
        World world = Bukkit.getWorld("world");

        world.setGameRuleValue("randomTickSpeed", "0");
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setPVP(false);
        world.setThundering(false);
        world.setTime(0);
        world.setWeatherDuration(0);

        ListenerLoader.loadListeners(this, "com.lothus.lobby.punish.menus");
        ListenerLoader.loadListeners(this, "com.lothus.lobby.punish.listeners");
        BukkitCommandLoader.loadCommands(this, "com.lothus.lobby.punish.command");
    }

    @Override
    public void onDisable() {

    }
}
