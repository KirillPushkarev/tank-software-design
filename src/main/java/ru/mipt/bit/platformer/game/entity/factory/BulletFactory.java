package ru.mipt.bit.platformer.game.entity.factory;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.ProgressCalculator;
import ru.mipt.bit.platformer.game.entity.Bullet;
import ru.mipt.bit.platformer.game.entity.Direction;
import ru.mipt.bit.platformer.game.entity.Level;

public class BulletFactory {
    private final ProgressCalculator progressCalculator;
    private Level level;

    public BulletFactory(ProgressCalculator progressCalculator) {
        this.progressCalculator = progressCalculator;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Bullet createBullet(GridPoint2 tankCoordinates, Direction tankDirection) {
        var initialCoordinates = new GridPoint2(tankCoordinates);
        return new Bullet(initialCoordinates, Level.BULLET_WIDTH, Level.BULLET_HEIGHT, tankDirection, progressCalculator, level);
    }
}
