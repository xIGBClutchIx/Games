package me.clutchy.games

interface Game {

    fun minimumPlayers(): Int = 2
    fun maximumPlayers(): Int = 10

    fun onLobbyStart()
    fun onLobbyTick()
    fun onLobbyEnd()

    fun onGameStarting()
    fun onGameStart()
    fun onGameTick()
    fun onGameEnd()

    fun runMode(): RunMode = RunMode.AUTOMATIC

    enum class RunState {
        LOBBY_START, LOBBY_TICK, LOBBY_END,
        GAME_STARTING, GAME_START, GAME_TICK, GAME_END
    }

    enum class RunMode {
        AUTOMATIC, MANUAL
    }
}
