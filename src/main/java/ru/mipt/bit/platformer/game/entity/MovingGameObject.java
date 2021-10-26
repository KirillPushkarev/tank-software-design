package ru.mipt.bit.platformer.game.entity;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.ColliderManager;
import ru.mipt.bit.platformer.game.Direction;
import ru.mipt.bit.platformer.game.ProgressCalculator;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class MovingGameObject extends GameObject {
    public static final float MOVEMENT_PROGRESS_START = 0f;
    public static final float MOVEMENT_PROGRESS_END = 1f;
    protected final float timeOfPassingOneTile;

    protected final ProgressCalculator progressCalculator;
    protected final ColliderManager colliderManager;
    protected GridPoint2 destinationGridCoordinates = null;
    protected Direction lastDirection = Direction.NONE;
    protected float movementProgress = MOVEMENT_PROGRESS_END;

    public MovingGameObject(GridPoint2 initialCoordinates,
                            int width,
                            int height,
                            float timeOfPassingOneTile,
                            ProgressCalculator progressCalculator,
                            ColliderManager colliderManager) {
        super(initialCoordinates, width, height);
        this.timeOfPassingOneTile = timeOfPassingOneTile;
        this.progressCalculator = progressCalculator;
        this.colliderManager = colliderManager;
    }

    public GridPoint2 getDestinationGridCoordinates() {
        return destinationGridCoordinates;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public boolean isMoving() {
        return destinationGridCoordinates != null;
    }

    public void move(Direction direction, float deltaTime) {
        handleRotation(direction);
        updateMovement(deltaTime);
    }

    private void handleRotation(Direction direction) {
        if (!isMoving() && direction != Direction.NONE) {
            this.lastDirection = direction;
            rotate(this.lastDirection.getRotation());

            if (!colliderManager.hasCollisionInDirection(getGridCoordinates(), this.lastDirection)) {
                startMovement();
            }
        }
    }

    private void startMovement() {
        destinationGridCoordinates = this.lastDirection.getNextCoordinates(gridCoordinates);
        movementProgress = MOVEMENT_PROGRESS_START;
    }

    private void updateMovement(float deltaTime) {
        if (isMoving()) {
            movementProgress = progressCalculator.continueProgress(movementProgress, deltaTime, timeOfPassingOneTile);

            if (isEqual(movementProgress, MOVEMENT_PROGRESS_END)) {
                finishMovement();
            }
        }
    }

    private void finishMovement() {
        gridCoordinates.set(destinationGridCoordinates);
        destinationGridCoordinates = null;
    }

    @Override
    public boolean holdsPosition(GridPoint2 position) {
        return super.holdsPosition(position) || isMoving() && destinationGridCoordinates.equals(position);
    }
}
