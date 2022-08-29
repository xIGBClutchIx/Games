package me.clutchy.games;

public interface Game {

    void onLobbyStart();
    void onLobbyEnd();
    void onLobbyTick();

    void onGameStarting();
    void onGameStart();
    void onGameEnd();
    void onGameTick();

    int minimumPlayers();
    int maximumPlayers();

    RunMode runMode();

    public enum RunMode {
        AUTOMATIC,
        MANUAL
    }
}
