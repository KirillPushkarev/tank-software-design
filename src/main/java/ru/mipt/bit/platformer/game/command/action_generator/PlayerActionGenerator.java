package ru.mipt.bit.platformer.game.command.action_generator;

import ru.mipt.bit.platformer.game.entity.Action;
import ru.mipt.bit.platformer.game.entity.CommandType;
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
    public List<Action> getActions(GameObject gameObject) {
        List<Action> actions = new ArrayList<>();
        var direction = inputToDirectionMapper.getDirection();
        actions.add(new Action(CommandType.MOVE, direction));

        return actions;
    }
}
