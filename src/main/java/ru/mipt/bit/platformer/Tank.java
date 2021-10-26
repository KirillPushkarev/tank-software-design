package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.ColliderManager;
import ru.mipt.bit.platformer.game.Direction;
import ru.mipt.bit.platformer.game.MovingGameObject;
import ru.mipt.bit.platformer.game.ProgressCalculator;

public class Tank {
    private final MovingGameObject movingGameObject;

    public Tank(GridPoint2 initialCoordinates, int width, int height, ProgressCalculator progressCalculator, ColliderManager colliderManager) {
        movingGameObject = new MovingGameObject(initialCoordinates, width, height, progressCalculator, colliderManager);
    }

    public MovingGameObject getMovingGameObject() {
        return movingGameObject;
    }

    public void move(Direction direction, float deltaTime) {
        movingGameObject.move(direction, deltaTime);
    }
}
