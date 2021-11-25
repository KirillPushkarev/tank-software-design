package ru.mipt.bit.platformer.game.entity;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.ProgressCalculator;
import ru.mipt.bit.platformer.game.collision.ColliderManager;

import java.util.List;

public class Level {
    public static final int TREE_WIDTH = 128;
    public static final int OBSTACLE_HEIGHT = 128;

    public static final int TANK_WIDTH = 92;
    public static final int TANK_HEIGHT = 84;

    private final int levelWidth;
    private final int levelHeight;
    private final ColliderManager colliderManager;
    private final ProgressCalculator progressCalculator;
    private Player player;
    private final List<GameObject> obstacles;
    private final List<Tank> tanks;

    public Level(int levelWidth,
                 int levelHeight,
                 Player player,
                 List<GameObject> obstacles,
                 List<Tank> tanks,
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

    public Player getPlayer() {
        return player;
    }

    public void addPlayer(int x, int y) {
        GridPoint2 coordinates = new GridPoint2(x, y);
        this.player = new Player(coordinates, TANK_WIDTH, TANK_HEIGHT, progressCalculator, colliderManager);
    }

    public List<GameObject> getObstacles() {
        return obstacles;
    }

    public List<Tank> getTanks() {
        return tanks;
    }
}
