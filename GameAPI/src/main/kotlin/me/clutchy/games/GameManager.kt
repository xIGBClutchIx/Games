package me.clutchy.games

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import java.util.*

class GameManager(private val api: GameAPI,
                  private val baseTypes: HashMap<String, Class<out Game>> = hashMapOf(), // sg - BASE GAME
                  private val games: HashMap<String, Game?> = hashMapOf(), // sg-random - running game
                  private val tasks: HashMap<String, BukkitTask> = hashMapOf(), // sg-random - task
                  private val states: HashMap<String, Game.RunState> = hashMapOf(), // sg-random - state
                  val players: HashMap<String, MutableList<UUID>> = hashMapOf() // sg-random - list players
) {
    // Automatic registering a base game
    fun registerGame(id: String, game: Class<out Game>) {
        baseTypes[id] = game
    }

    // Command - /game create sg - make a new game with type
    fun createGame(sender: CommandSender, game: String) {
        // Check we have that base game type
        if (baseTypes.containsKey(game)) {
            // TODO: Check random and shit - boring bleh
            val randomGameID: String = game + "-" + UUID::randomUUID.toString()
            // Create a new game from class
            val nameGameFromType = baseTypes[game]?.getConstructor()?.newInstance()
            games[randomGameID] = nameGameFromType
            tasks[randomGameID] = Bukkit.getScheduler().runTaskLaterAsynchronously(api, GameRunnable(nameGameFromType), 20)
            states[randomGameID] = Game.RunState.GAME_STARTING
            players[randomGameID] = mutableListOf()
        } else {
            sender.sendMessage("Sorry this game type is not registered!")
        }
    }

    // Command - /game kill sg-random
    fun killGame(sender: CommandSender, gameId: String) {
        if (games.containsKey(gameId) and tasks.containsKey(gameId)) {
            games.remove(gameId)
            tasks.remove(gameId)
            states.remove(gameId)
            // Remove from any current games
            players.forEach { (foundGameID, players) ->
                players.forEach { player ->
                    // Inform all players in game
                    val playerInGame =  Bukkit.getPlayer(player)
                    playerInGame?.sendMessage("Game killed: $foundGameID")
                    // Send to Lobby
                    playerInGame?.sendToLobby()
                }
                if (sender is Player && !players.contains(sender.uniqueId)) sender.sendMessage("Game killed: $foundGameID")
            }
            // Remove players after
            players.remove(gameId)
        } else {
            sender.sendMessage("Game doesn't exist!")
        }
    }

    // Command - /game find username
    fun findPlayer(sender: CommandSender, uuid: UUID) {
        // Check for that actual player
        val players = players.filterValues { uuids -> uuids.contains(uuid) }
        // Inform if found or not
        if (players.isNotEmpty()) {
            sender.sendMessage("Player found: ${players.keys}")
        } else {
            sender.sendMessage("Player is not in a game!")
        }
    }

    // Command - /game join sg-random username
    fun addPlayer(sender: CommandSender, gameID: String, player: Player) {
        // Make sure game exists
        if (games.containsKey(gameID)) {
            // Remove from any current games
            players.forEach { (foundGameID, players) ->
                if (players.remove(player.uniqueId)) {
                    // Inform
                    player.sendMessage("Removed from game: $foundGameID")
                    if (sender != player) sender.sendMessage("Removed (${player.name}) from game: $foundGameID")
                }
            }
            // Get current players for that game id
            val listPlayers = players[gameID]
            if (listPlayers != null) {
                // Add to game
                listPlayers.add(player.uniqueId)
                players[gameID] = listPlayers
                // Inform
                player.sendMessage("Added to game: $gameID")
                if (sender != player) sender.sendMessage("Added (${player.name}) to game: $gameID")
                // TODO The game runnable will take care of this
                //player.sendToWorld(gameID)
            }
        } else {
            sender.sendMessage("Game doesn't exist!")
        }
    }

    // Command - /game remove username
    fun removePlayer(sender: CommandSender, player: Player) {
        // Remove from any current games
        players.forEach { (foundGameID, players) ->
            // Inform if found and removed
            if (players.remove(player.uniqueId)) {
                // Inform
                player.sendMessage("Left the game: $foundGameID")
                if (sender != player) sender.sendMessage("Left (${player.name}) the game: $foundGameID")
            }
        }
        // Send to Lobby
        player.sendToLobby()
    }
}
