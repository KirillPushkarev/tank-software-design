package ru.mipt.bit.platformer.game.entity;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.ProgressCalculator;
import ru.mipt.bit.platformer.game.collision.ColliderManager;
import ru.mipt.bit.platformer.game.entity.factory.BulletFactory;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Tank extends GameObject {
    public static final float MOVEMENT_PROGRESS_START = 0f;
    public static final float MOVEMENT_PROGRESS_END = 1f;
    private static final float MIN_SHOOT_INTERVAL = 1.0f;
    private static final int INITIAL_LIVES = 3;
    protected final float timeOfPassingOneTile;

    protected final ProgressCalculator progressCalculator;
    protected final ColliderManager colliderManager;
    private final BulletFactory bulletFactory;
    private final Level level;
    protected GridPoint2 destinationCoordinates = null;
    protected Direction lastDirection = Direction.UP;
    protected float movementProgress = MOVEMENT_PROGRESS_END;
    private float timeSinceLastShoot = 0f;
    private int lives = INITIAL_LIVES;
    private DamageState damageState = new NoDamageState();

    public Tank(GridPoint2 initialCoordinates,
                int width,
                int height,
                float timeOfPassingOneTile,
                ProgressCalculator progressCalculator,
                BulletFactory bulletFactory,
                Level level) {
        super(initialCoordinates, width, height);
        this.timeOfPassingOneTile = timeOfPassingOneTile;
        this.progressCalculator = progressCalculator;
        this.colliderManager = level.getColliderManager();
        this.bulletFactory = bulletFactory;
        this.level = level;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public Direction getLastDirection() {
        return lastDirection;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public boolean isMoving() {
        return destinationCoordinates != null;
    }

    @Override
    public boolean holdsPosition(GridPoint2 position) {
        return super.holdsPosition(position) || isMoving() && destinationCoordinates.equals(position);
    }

    @Override
    public void move(Direction direction) {
        updateRotation(direction);
        if (!isMoving() && direction != Direction.NONE && colliderManager.canMoveInDirection(coordinates, this.lastDirection)) {
            startMovement();
        }
    }

    @Override
    public void shoot() {
        if (timeSinceLastShoot > MIN_SHOOT_INTERVAL && colliderManager.canMoveInDirection(coordinates, this.lastDirection)) {
            var bullet = bulletFactory.createBullet(coordinates, lastDirection);
            level.registerBullet(bullet);
            timeSinceLastShoot = 0;
        }
    }

    public void liveTimePeriod(float deltaTime) {
        updatePosition(deltaTime);
        timeSinceLastShoot += deltaTime;
    }

    public void applyDamage(int damage) {
        lives -= damage;
        if (lives <= 0) {
            level.unregisterTank(this);
        }

        damageState = damageState.getNextState();
    }

    private void updateRotation(Direction direction) {
        if (!isMoving() && direction != Direction.NONE) {
            lastDirection = direction;
            rotate(lastDirection.getRotation());
        }
    }

    private void startMovement() {
        destinationCoordinates = this.lastDirection.getNextCoordinates(coordinates);
        movementProgress = MOVEMENT_PROGRESS_START;
    }

    private void updatePosition(float deltaTime) {
        if (isMoving()) {
            movementProgress = progressCalculator.continueProgress(movementProgress, deltaTime, timeOfPassingOneTile * damageState.getMoveSpeedMultiplier());

            if (isEqual(movementProgress, MOVEMENT_PROGRESS_END)) {
                finishMovement();
            }
        }
    }

    private void finishMovement() {
        coordinates.set(destinationCoordinates);
        destinationCoordinates = null;
    }
}
