package ru.mipt.bit.platformer.game.entity;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.ProgressCalculator;
import ru.mipt.bit.platformer.game.collision.ColliderManager;
import ru.mipt.bit.platformer.game.common.Event;
import ru.mipt.bit.platformer.game.common.EventType;
import ru.mipt.bit.platformer.game.common.Subscriber;
import ru.mipt.bit.platformer.game.entity.factory.BulletFactory;

import java.util.ArrayList;
import java.util.List;

public class Level {
    public static final int TREE_WIDTH = 128;
    public static final int TREE_HEIGHT = 128;
    public static final int TANK_WIDTH = 92;
    public static final int TANK_HEIGHT = 84;
    public static final int BULLET_WIDTH = 20;
    public static final int BULLET_HEIGHT = 10;
    public static final float TANK_TIME_OF_PASSING_ONE_TILE = 0.4f;
    public static final float PLAYER_TIME_OF_PASSING_ONE_TILE = 0.3f;

    private final int levelWidth;
    private final int levelHeight;
    private final ColliderManager colliderManager;
    private final ProgressCalculator progressCalculator;
    private final BulletFactory bulletFactory;
    private Tank player;
    private final List<Obstacle> obstacles;
    private final List<Tank> tanks;
    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Tank> tanksToUnregister = new ArrayList<>();
    private final List<Bullet> bulletsToUnregister = new ArrayList<>();

    private final List<Subscriber> subscribers = new ArrayList<>();

    public Level(int levelWidth,
                 int levelHeight,
                 Tank player,
                 List<Obstacle> obstacles,
                 List<Tank> tanks,
                 ColliderManager colliderManager,
                 ProgressCalculator progressCalculator,
                 BulletFactory bulletFactory) {
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.player = player;
        this.obstacles = obstacles;
        this.tanks = tanks;
        this.colliderManager = colliderManager;
        this.progressCalculator = progressCalculator;
        this.bulletFactory = bulletFactory;

        colliderManager.setLevel(this);
        bulletFactory.setLevel(this);
        bulletFactory.setProgressCalculator(progressCalculator);
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public Tank getPlayer() {
        return player;
    }

    public void addPlayer(int x, int y) {
        GridPoint2 coordinates = new GridPoint2(x, y);
        this.player = new Tank(coordinates, Level.TANK_WIDTH, Level.TANK_HEIGHT, Level.PLAYER_TIME_OF_PASSING_ONE_TILE, progressCalculator, bulletFactory, this);
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void addObstacle(int x, int y) {
        GridPoint2 coordinates = new GridPoint2(x, y);
        var obstacle = new Obstacle(coordinates, Level.TREE_WIDTH, Level.TREE_HEIGHT);
        obstacles.add(obstacle);
    }

    public List<Tank> getTanks() {
        return tanks;
    }

    public void addTank(int x, int y) {
        GridPoint2 coordinates = new GridPoint2(x, y);
        var tank = new Tank(coordinates, Level.TANK_WIDTH, Level.TANK_HEIGHT, Level.TANK_TIME_OF_PASSING_ONE_TILE, progressCalculator, bulletFactory, this);
        tanks.add(tank);
    }

    public ColliderManager getColliderManager() {
        return colliderManager;
    }

    public void liveTimePeriod(float deltaTime) {
        player.liveTimePeriod(deltaTime);
        for (GameObject obstacle : obstacles) {
            obstacle.liveTimePeriod(deltaTime);
        }
        for (Tank tank : tanks) {
            tank.liveTimePeriod(deltaTime);
        }
        for (Bullet bullet : bullets) {
            bullet.liveTimePeriod(deltaTime);
        }

        clearUnregisteredObjects();
    }

    private void clearUnregisteredObjects() {
        for (Bullet bullet : bulletsToUnregister) {
            bullets.remove(bullet);
        }
        bulletsToUnregister.clear();

        for (Tank tank : tanksToUnregister) {
            tanks.remove(tank);
        }
        tanksToUnregister.clear();
    }

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);

        subscriber.notify(new Event(EventType.ObjectAdded, player));
        for (GameObject obstacle : obstacles) {
            subscriber.notify(new Event(EventType.ObjectAdded, obstacle));
        }
        for (Tank tank : tanks) {
            subscriber.notify(new Event(EventType.ObjectAdded, tank));
        }
        for (GameObject bullet : bullets) {
            subscriber.notify(new Event(EventType.ObjectAdded, bullet));
        }
    }

    public void registerBullet(Bullet bullet) {
        bullets.add(bullet);
        notifySubscribers(new Event(EventType.ObjectAdded, bullet));
    }

    public void unregisterBullet(Bullet bullet) {
        bulletsToUnregister.add(bullet);
        notifySubscribers(new Event(EventType.ObjectRemoved, bullet));
    }

    public void unregisterTank(Tank tank) {
        tanksToUnregister.add(tank);
        notifySubscribers(new Event(EventType.ObjectRemoved, tank));
    }

    public void notifySubscribers(Event event) {
        for (Subscriber subscriber : subscribers) {
            subscriber.notify(event);
        }
    }
}
