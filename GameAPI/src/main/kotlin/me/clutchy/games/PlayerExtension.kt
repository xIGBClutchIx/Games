package me.clutchy.games

import org.bukkit.Bukkit
import org.bukkit.entity.Player

public fun Player.sendToLobby() {
    sendToWorld("Lobby")
}

public fun Player.sendToWorld(world: String) {
    this.teleport(Bukkit.getWorld(world)!!.spawnLocation)
}
