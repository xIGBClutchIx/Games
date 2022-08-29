package me.clutchy.games

import org.bukkit.Bukkit
import org.bukkit.WorldCreator
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.annotation.command.Command
import org.bukkit.plugin.java.annotation.command.Commands
import org.bukkit.plugin.java.annotation.dependency.Libraries
import org.bukkit.plugin.java.annotation.dependency.Library
import org.bukkit.plugin.java.annotation.plugin.ApiVersion
import org.bukkit.plugin.java.annotation.plugin.Plugin
import java.io.File

@Plugin(name = "GameAPI", version = "1.0.0")
@Commands(Command(name = "game"))
@ApiVersion(ApiVersion.Target.v1_19)
@Libraries(Library("org.jetbrains.kotlin:kotlin-reflect:1.7.10"))
class GameAPI: JavaPlugin() {

    val gameManager: GameManager = GameManager(this)

    override fun onEnable() {
        val world = server.getWorld("Lobby")
        if (world == null) {
            val lobbyFolder = File("Lobby")
            if (lobbyFolder.exists()) {
                if (!lobbyFolder.isDirectory) {
                    lobbyFolder.delete()
                    // TODO: Copy lobby into folder
                }
            } else {
                // TODO: Copy lobby into folder
            }
            Bukkit.createWorld(WorldCreator("Lobby").generateStructures(false).generator(VoidGenerator()))
        }
        server.pluginManager.registerEvents(BasicListener(), this)
        getCommand("game")!!.setExecutor(GameCommand())
    }

    override fun onDisable() {

    }

    fun registerGame(id: String, game: Class<out Game>) {
        gameManager.registerGame(this, id, game)
    }
}
