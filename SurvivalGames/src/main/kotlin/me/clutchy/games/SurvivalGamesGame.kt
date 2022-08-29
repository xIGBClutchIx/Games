package me.clutchy.games

import me.clutchy.games.Game.RunMode
import me.clutchy.games.Game.RunState

class SurvivalGamesGame: Game {

    override fun id(): String {
        return "SG"
    }

    override fun minimumPlayers(): Int {
        return 2
    }

    override fun maximumPlayers(): Int {
        return 10
    }

    override fun onLobbyStart() {}
    override fun onLobbyEnd() {}
    override fun onLobbyTick() {}
    override fun onGameStarting() {}
    override fun onGameStart() {}
    override fun onGameEnd() {}
    override fun onGameTick() {}

    override fun runState(): RunState {
        return RunState.LOBBY_START
    }

    override fun runMode(): RunMode {
        return RunMode.AUTOMATIC
    }
}
