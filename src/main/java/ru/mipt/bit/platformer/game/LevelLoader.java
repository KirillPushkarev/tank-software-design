package ru.mipt.bit.platformer.game;

import ru.mipt.bit.platformer.GameDesktopLauncher;
import ru.mipt.bit.platformer.game.entity.Level;
import ru.mipt.bit.platformer.game.level_generator.StreamLevelGenerator;
import ru.mipt.bit.platformer.util.FileUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LevelLoader {
    public Level getLevel() {
        Level level = null;
        try (var inputStream = new BufferedReader(new FileReader(FileUtils.readFileFromResources(GameDesktopLauncher.LEVEL_OBJECT_MAP)))) {
            level = new Level(
                    new StreamLevelGenerator(inputStream).generateLevelLayout(GameDesktopLauncher.LEVEL_WIDTH, GameDesktopLauncher.LEVEL_HEIGHT),
                    GameDesktopLauncher.LEVEL_WIDTH,
                    GameDesktopLauncher.LEVEL_HEIGHT
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return level;
    }
}
