package ru.mipt.bit.platformer.game.command.action_generator;

import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.AI;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.immovable.Obstacle;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import ru.mipt.bit.platformer.game.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AIActionGenerator implements ActionGenerator {
    private final AI ai;
    private final Level level;

    public AIActionGenerator(AI ai, Level level) {
        this.ai = ai;
        this.level = level;
    }

    @Override
    public List<Action> getActions(GameObject gameObject) {
        List<Action> actions = new ArrayList<>();

        List<Recommendation> recommendations = ai.recommend(getGameState());
        for (Recommendation recommendation : recommendations) {
            addActionForRecommendation(gameObject, actions, recommendation);
        }

        return actions;
    }

    private void addActionForRecommendation(GameObject gameObject, List<Action> actions, Recommendation recommendation) {
        var actor = recommendation.getActor();
        var action = recommendation.getAction();

        if (actor.getSource().equals(gameObject)) {
            var mappedAction = mapAction(action);
            if (mappedAction != null) {
                actions.add(mappedAction);
            }
        }
    }

    private GameState getGameState() {
        var builder = new GameState.GameStateBuilder()
                .levelWidth(level.getLevelWidth())
                .levelHeight(level.getLevelHeight())
                .obstacles(getObstacles(level.getObstacles()))
                .bots(getBots(level.getTanks()))
                .player(getPlayer(level.getPlayer()));

        return builder.build();
    }

    private List<Obstacle> getObstacles(List<GameObject> obstacles) {
        return obstacles.stream()
                .map(ob -> {
                    GridPoint2 coordinates = ob.getCoordinates();
                    return new Obstacle(coordinates.x, coordinates.y);
                })
                .collect(Collectors.toList());
    }

    private List<Bot> getBots(List<Tank> tanks) {
        return tanks.stream()
                .map(this::getBotFromTank)
                .collect(Collectors.toList());
    }

    private Bot getBotFromTank(Tank tank) {
        var movingGameObject = tank.getMovingGameObject();
        var coordinates = movingGameObject.getCoordinates();
        var destCoordinates = movingGameObject.getCoordinates();

        return new Bot.BotBuilder()
                .source(movingGameObject)
                .x(coordinates.x)
                .y(coordinates.y)
                .destX(destCoordinates.x)
                .destY(destCoordinates.y)
                .orientation(mapDirectionToOrientation(movingGameObject.getLastDirection()))
                .build();
    }

    private org.awesome.ai.state.movable.Player getPlayer(Player player) {
        var movingGameObject = player.getMovingGameObject();
        var coordinates = movingGameObject.getCoordinates();
        var destCoordinates = movingGameObject.getCoordinates();
        Orientation orientation = mapDirectionToOrientation(movingGameObject.getLastDirection());

        return new org.awesome.ai.state.movable.Player.PlayerBuilder()
                .source(movingGameObject)
                .x(coordinates.x)
                .y(coordinates.y)
                .destX(destCoordinates.x)
                .destY(destCoordinates.y)
                .orientation(orientation)
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

    private Action mapAction(org.awesome.ai.Action action) {
        switch (action) {
            case MoveNorth:
                return new Action(CommandType.MOVE, Direction.UP);
            case MoveEast:
                return new Action(CommandType.MOVE, Direction.LEFT);
            case MoveWest:
                return new Action(CommandType.MOVE, Direction.RIGHT);
            case MoveSouth:
                return new Action(CommandType.MOVE, Direction.DOWN);
            default:
                return null;
        }
    }
}
