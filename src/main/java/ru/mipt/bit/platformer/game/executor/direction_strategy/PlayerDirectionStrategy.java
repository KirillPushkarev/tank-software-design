package ru.mipt.bit.platformer.game.executor.direction_strategy;

import ru.mipt.bit.platformer.game.entity.Direction;
import ru.mipt.bit.platformer.game.entity.GameObject;
import ru.mipt.bit.platformer.game.input.InputToDirectionMapper;

public class PlayerDirectionStrategy implements DirectionStrategy {
    private final InputToDirectionMapper inputToDirectionMapper;

    public PlayerDirectionStrategy(InputToDirectionMapper inputToDirectionMapper) {
        this.inputToDirectionMapper = inputToDirectionMapper;
    }

    public Direction getDirection(float deltaTime, GameObject gameObject) {
        return inputToDirectionMapper.getDirection();
    }
}
