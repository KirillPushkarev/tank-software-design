package ru.mipt.bit.platformer.game.entity;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.ProgressCalculator;
import ru.mipt.bit.platformer.game.collision.ColliderManager;

import java.util.List;

public class Level {
    public static final int TREE_WIDTH = 128;
    public static final int TREE_HEIGHT = 128;
    public static final int TANK_WIDTH = 92;
    public static final int TANK_HEIGHT = 84;
    public static final float TANK_TIME_OF_PASSING_ONE_TILE = 0.4f;
    public static final float PLAYER_TIME_OF_PASSING_ONE_TILE = 0.3f;

    private final int levelWidth;
    private final int levelHeight;
    private final ColliderManager colliderManager;
    private final ProgressCalculator progressCalculator;
    private MovingGameObject player;
    private final List<GameObject> obstacles;
    private final List<MovingGameObject> tanks;

    public Level(int levelWidth,
                 int levelHeight,
                 MovingGameObject player,
                 List<GameObject> obstacles,
                 List<MovingGameObject> tanks,
                 ColliderManager colliderManager,
                 ProgressCalculator progressCalculator) {
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.player = player;
        this.obstacles = obstacles;
        this.tanks = tanks;
        this.colliderManager = colliderManager;
        this.progressCalculator = progressCalculator;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public MovingGameObject getPlayer() {
        return player;
    }

    public void setPlayerPosition(int x, int y) {
        GridPoint2 coordinates = new GridPoint2(x, y);
        this.player = new MovingGameObject(
                coordinates,
                TANK_WIDTH,
                TANK_HEIGHT,
                PLAYER_TIME_OF_PASSING_ONE_TILE,
                progressCalculator,
                colliderManager
        );
    }

    public List<GameObject> getObstacles() {
        return obstacles;
    }

    public List<MovingGameObject> getTanks() {
        return tanks;
    }

    public void liveTimePeriod(float deltaTime) {
        player.liveTimePeriod(deltaTime);
        for (GameObject obstacle : obstacles) {
            obstacle.liveTimePeriod(deltaTime);
        }
        for (MovingGameObject tank : tanks) {
            tank.liveTimePeriod(deltaTime);
        }
    }
}
