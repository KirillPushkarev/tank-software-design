package ru.mipt.bit.platformer.game.executor.direction_strategy;

import org.awesome.ai.AI;
import org.awesome.ai.Action;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.immovable.Obstacle;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import ru.mipt.bit.platformer.game.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class AIDirectionStrategyAdapter implements DirectionStrategy {
    private final AI ai;
    private final Level level;

    public AIDirectionStrategyAdapter(AI ai, Level level) {
        this.ai = ai;
        this.level = level;
    }

    @Override
    public Direction getDirection(float deltaTime, GameObject gameObject) {
        List<Recommendation> recommendations = ai.recommend(getGameState());
        for (Recommendation recommendation : recommendations) {
            var actor = recommendation.getActor();
            var action = recommendation.getAction();

            if (actor.getSource().equals(gameObject)) {
                return mapActionToDirection(action);
            }
        }

        return Direction.NONE;
    }

    private GameState getGameState() {
        var builder = new GameState.GameStateBuilder();
        builder.levelWidth(level.getLevelWidth());
        builder.levelHeight(level.getLevelHeight());
        builder.obstacles(getObstacles(level.getObstacles()));
        builder.bots(getBots(level.getTanks()));
        builder.player(getPlayer(level.getPlayer()));

        return builder.build();
    }

    private List<Obstacle> getObstacles(List<GameObject> obstacles) {
        return obstacles.stream()
                .map(ob -> new Obstacle(ob.getGridCoordinates().x, ob.getGridCoordinates().y))
                .collect(Collectors.toList());
    }

    private List<Bot> getBots(List<Tank> tanks) {
        return tanks.stream()
                .map(t -> {
                            var movingGameObject = t.getMovingGameObject();
                            var coordinates = movingGameObject.getGridCoordinates();
                            var destCoordinates = movingGameObject.getGridCoordinates();

                            return new Bot.BotBuilder()
                                    .source(movingGameObject)
                                    .x(coordinates.x)
                                    .y(coordinates.y)
                                    .destX(destCoordinates.x)
                                    .destY(destCoordinates.y)
                                    .orientation(mapDirectionToOrientation(movingGameObject.getLastDirection()))
                                    .build();
                        }
                )
                .collect(Collectors.toList());
    }

    private org.awesome.ai.state.movable.Player getPlayer(Player player) {
        var movingGameObject = player.getMovingGameObject();
        var coordinates = movingGameObject.getGridCoordinates();
        var destCoordinates = movingGameObject.getGridCoordinates();

        return new org.awesome.ai.state.movable.Player.PlayerBuilder()
                .source(movingGameObject)
                .x(coordinates.x)
                .y(coordinates.y)
                .destX(destCoordinates.x)
                .destY(destCoordinates.y)
                .orientation(mapDirectionToOrientation(movingGameObject.getLastDirection()))
                .build();
    }

    private Orientation mapDirectionToOrientation(Direction direction) {
        switch (direction) {
            case UP:
                return Orientation.N;
            case LEFT:
                return Orientation.W;
            case RIGHT:
                return Orientation.E;
            default:
                return Orientation.S;
        }
    }

    private Direction mapActionToDirection(Action action) {
        switch (action) {
            case MoveNorth:
                return Direction.UP;
            case MoveEast:
                return Direction.LEFT;
            case MoveWest:
                return Direction.RIGHT;
            case MoveSouth:
                return Direction.DOWN;
            default:
                return Direction.NONE;
        }
    }
}
