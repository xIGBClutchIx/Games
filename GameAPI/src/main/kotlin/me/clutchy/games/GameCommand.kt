package me.clutchy.games

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class GameCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (args.isNullOrEmpty()) {
            sender.sendMessage("/game - [types, list, create, kill, find, join, remove]")
            return false
        } else {
            val gameAPI: GameAPI = Bukkit.getServer().pluginManager.getPlugin("GameAPI") as GameAPI
            val formattedArgs: List<String> = args.map { s -> s.lowercase() }
            when (formattedArgs[0]) {
                "types" -> {
                    sender.sendMessage("Types List - " + gameAPI.gameManager.baseTypes.keys)
                }
                "list" -> {
                    sender.sendMessage("Games List - " + gameAPI.gameManager.players.keys)
                }
                "create" -> {
                    if (args.getOrNull(1) != null) {
                        gameAPI.gameManager.createGame(sender, args[1])
                    } else {
                        sender.sendMessage("/game create - [type]")
                    }
                }
                "kill" -> {
                    if (args.getOrNull(1) != null) {
                        gameAPI.gameManager.killGame(sender, args[1])
                    } else {
                        sender.sendMessage("/game kill - [type]")
                    }
                }
                "find" -> {
                    if (args.getOrNull(1) != null) {
                        val player = Bukkit.getPlayerUniqueId(args[1])
                        if (player != null) {
                            gameAPI.gameManager.findPlayer(sender, player)
                        } else {
                            sender.sendMessage("/game find ${args[1]} - Player doesn't exist")
                        }
                    } else {
                        sender.sendMessage("/game find - [playerName]")
                    }
                }
                "join" -> {
                    if (args.getOrNull(1) != null) {
                        if (args.getOrNull(2) != null) {
                            // TODO offline players
                            val player = Bukkit.getPlayer(args[2])
                            if (player != null) {
                                gameAPI.gameManager.addPlayer(sender, args[1], player)
                            } else {
                                sender.sendMessage("/game join ${args[1]} ${args[2]} - Player is not online")
                            }
                        } else {
                            sender.sendMessage("/game join ${args[1]} - [playerName]")
                        }

                    } else {
                        sender.sendMessage("/game join - [gameID] [playerName]")
                    }
                }
                "remove" -> {
                    if (args.getOrNull(1) != null) {
                        // TODO offline players
                        val player = Bukkit.getPlayer(args[1])
                        if (player != null) {
                            gameAPI.gameManager.removePlayer(sender, player)
                        } else {
                            sender.sendMessage("/game remove ${args[1]} - Player doesn't exist")
                        }
                    } else {
                        sender.sendMessage("/game remove - [playerName]")
                    }
                }
                else -> {
                    sender.sendMessage("/game - [types, list, create, kill, find, join, remove]")
                }
            }
        }
        return false
    }
}