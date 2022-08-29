package me.clutchy.games

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import java.util.UUID
import java.util.function.BiConsumer

class GameManager(private val api: GameAPI,
                  private val baseTypes: HashMap<String, Game> = hashMapOf(), // sg - BASE GAME
                  private val games: HashMap<String, Game?> = hashMapOf(), // sg-random - running game
                  private val tasks: HashMap<String, BukkitTask> = hashMapOf(), // sg-random - task
                  val players: HashMap<String, MutableList<UUID>> = hashMapOf() // sg-random - list players
) {
    // Automatic registering a base game
    fun registerGame(game: Game) {
        baseTypes[game.id()] = game
    }

    // Command - /game create sg
    fun createGame(player: Player, game: String) {
        // Check we have that base game type
        if (baseTypes.containsKey(game)) {
            // TODO: Check random and shit - boring bleh
            val randomGameID: String = game + "-" + UUID::randomUUID.toString()
            val foundGame = baseTypes[game]
            games[randomGameID] = foundGame
            tasks[randomGameID] = Bukkit.getScheduler().runTaskLaterAsynchronously(api, GameRunnable(foundGame), 20)
            players[randomGameID] = mutableListOf()
        } else {
            player.sendMessage("Sorry this game type is not registered!")
        }
    }

    // Command - /game kill sg-random
    fun killGame(player: Player, gameId: String) {
        if (games.containsKey(gameId) and tasks.containsKey(gameId)) {
            games.remove(gameId)
            tasks.remove(gameId)
            players.remove(gameId)
        } else {
            player.sendMessage("Game doesn't exist!")
        }
    }

    // Command - /game join sg-random name
    fun addPlayer(game: String, player: Player) {
        // Make sure game exists
        if (games.containsKey(game)) {
            // Remove from any current games
            players.forEach { (gameID, players) ->
                if (players.remove(player.uniqueId)) {
                    player.sendMessage("Removed from game: $gameID")
                }
            }
            // Get current players for that game id
            val listPlayers = players[game]
            if (listPlayers != null) {
                // Add to game
                player.sendMessage("Added to game: $game")
                listPlayers.add(player.uniqueId)
                players[game] = listPlayers
                player.sendToWorld(game)
            }
        } else {
            player.sendMessage("Game doesn't exist!")
        }
    }

    // Command - /game leave name
    fun removePlayer(player: Player) {
        // Remove from any current games
        players.forEach { (gameID, players) ->
            if (players.remove(player.uniqueId)) {
                player.sendMessage("Removed from game: $gameID")
            }
        }
        // Send to Lobby
        player.sendToLobby()
    }
}
