package ru.mipt.bit.platformer.game.level_generator;

import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.game.ProgressCalculator;
import ru.mipt.bit.platformer.game.collision.ColliderManager;
import ru.mipt.bit.platformer.game.command.CommandExecutor;
import ru.mipt.bit.platformer.game.command.action_generator.AIActionGenerator;
import ru.mipt.bit.platformer.game.command.action_generator.ActionGenerator;
import ru.mipt.bit.platformer.game.command.action_generator.PlayerActionGenerator;
import ru.mipt.bit.platformer.game.entity.Level;
import ru.mipt.bit.platformer.game.entity.factory.BulletFactory;
import ru.mipt.bit.platformer.game.input.InputToActionMapper;

import java.io.IOException;

public abstract class LevelGenerator {
    public static final int GRASS_MARKER = 0;
    public static final int OBSTACLE_MARKER = 1;
    public static final int TANK_MARKER = 2;
    public static final int PLAYER_MARKER = 3;

    private final ColliderManager colliderManager = new ColliderManager();
    private final ProgressCalculator progressCalculator = new ProgressCalculator();
    private final BulletFactory bulletFactory = new BulletFactory(progressCalculator);
    private final CommandExecutor playerCommandExecutor;
    private final CommandExecutor botCommandExecutor;
    protected Level level;

    public LevelGenerator() {
        InputToActionMapper inputToActionMapper = new InputToActionMapper();
        ActionGenerator playerActionGenerator = new PlayerActionGenerator(inputToActionMapper);
        playerCommandExecutor = new CommandExecutor(playerActionGenerator);
        ActionGenerator botActionGenerator = new AIActionGenerator(new NotRecommendingAI());
        botCommandExecutor = new CommandExecutor(botActionGenerator);
    }

    public Level generateLevel(int levelWidth, int levelHeight) throws IOException {
        level = new Level(levelWidth,
                levelHeight,
                colliderManager,
                progressCalculator,
                bulletFactory,
                playerCommandExecutor,
                botCommandExecutor);
        var levelLayout = generateLevelLayout(levelWidth, levelHeight);
        generateObjectsFromGrid(levelLayout, levelWidth, levelHeight);

        return level;
    }

    protected abstract int[][] generateLevelLayout(int gridWidth, int gridHeight) throws IOException;

    private void generateObjectsFromGrid(int[][] grid, int levelWidth, int levelHeight) {
        for (int i = 0; i < levelHeight; i++) {
            for (int j = 0; j < levelWidth; j++) {
                int x = j;
                int y = grid.length - 1 - i;

                if (grid[i][j] == OBSTACLE_MARKER) {
                    level.addObstacle(x, y);
                }

                if (grid[i][j] == TANK_MARKER) {
                    level.addTank(x, y);
                }

                if (grid[i][j] == PLAYER_MARKER) {
                    level.addPlayer(x, y);
                }
            }
        }
    }
}
