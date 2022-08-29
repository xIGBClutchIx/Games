package me.clutchy.games;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Plugin;

import java.io.File;

@Plugin(name = "GameAPI", version = "1.0.0")
public class GameAPI extends JavaPlugin {

    @Override
    public void onEnable() {
        World world = getServer().getWorld("Lobby");
        if (world == null) {
            File lobbyFolder = new File("Lobby");
            if (lobbyFolder.exists()) {
                if (!lobbyFolder.isDirectory()) {
                     lobbyFolder.delete();
                     // TODO: Copy lobby into folder
                }
            } else {
                // TODO: Copy lobby into folder
            }
            Bukkit.createWorld(new WorldCreator("Lobby").generateStructures(false).generator(new VoidGenerator()));
        }
        getServer().getPluginManager().registerEvents(new TestListener(), this);
    }

    @Override
    public void onDisable() {

    }
}
