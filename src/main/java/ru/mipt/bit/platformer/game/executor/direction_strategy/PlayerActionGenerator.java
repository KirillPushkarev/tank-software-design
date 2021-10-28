package ru.mipt.bit.platformer.game.executor.direction_strategy;

import ru.mipt.bit.platformer.game.entity.Action;
import ru.mipt.bit.platformer.game.entity.ActionType;
import ru.mipt.bit.platformer.game.entity.GameObject;
import ru.mipt.bit.platformer.game.input.InputToDirectionMapper;

import java.util.ArrayList;
import java.util.List;

public class PlayerActionGenerator implements ActionGenerator {
    private final InputToDirectionMapper inputToDirectionMapper;

    public PlayerActionGenerator(InputToDirectionMapper inputToDirectionMapper) {
        this.inputToDirectionMapper = inputToDirectionMapper;
    }

    @Override
    public List<Action> getActions(GameObject gameObject, float deltaTime) {
        List<Action> actions = new ArrayList<>();
        var direction = inputToDirectionMapper.getDirection();
        actions.add(new Action(ActionType.MOVE, direction));

        return actions;
    }
}
