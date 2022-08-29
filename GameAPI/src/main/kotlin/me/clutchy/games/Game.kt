package me.clutchy.games

interface Game {

    fun onLobbyStart()
    fun onLobbyEnd()
    fun onLobbyTick()
    fun onGameStarting()
    fun onGameStart()
    fun onGameEnd()
    fun onGameTick()
    fun minimumPlayers(): Int = 2
    fun maximumPlayers(): Int = 10
    fun runMode(): RunMode = RunMode.MANUAL

    enum class RunMode {
        AUTOMATIC, MANUAL
    }
}
