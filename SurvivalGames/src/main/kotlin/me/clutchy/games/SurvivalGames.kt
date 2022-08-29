package me.clutchy.games

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.annotation.dependency.Dependency
import org.bukkit.plugin.java.annotation.dependency.DependsOn
import org.bukkit.plugin.java.annotation.plugin.ApiVersion
import org.bukkit.plugin.java.annotation.plugin.Plugin

@Plugin(name = "SurvivalGames", version = "1.0.0")
@ApiVersion(ApiVersion.Target.v1_19)
@DependsOn(Dependency("GameAPI"))
class SurvivalGames: JavaPlugin() {

    override fun onEnable() {
        (Bukkit.getServer().pluginManager.getPlugin("GameAPI") as GameAPI).registerGame("SG", SurvivalGamesGame::class.java)
    }
}
