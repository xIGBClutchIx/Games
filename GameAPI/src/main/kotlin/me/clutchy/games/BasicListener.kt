package me.clutchy.games

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class BasicListener: Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        // TODO Check if player is in any game and if so don't tp
        event.player.sendToLobby()
    }
}
