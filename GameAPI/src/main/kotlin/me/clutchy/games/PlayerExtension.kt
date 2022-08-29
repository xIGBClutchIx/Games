package me.clutchy.games

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Player

public fun Player.sendToLobby() {
    sendToWorld("Lobby")
}

public fun Player.sendToWorld(worldID: String) {
    val world: World? = Bukkit.getWorld(worldID)
    if (world != null) {
        this.teleport(world.spawnLocation)
    }
}
