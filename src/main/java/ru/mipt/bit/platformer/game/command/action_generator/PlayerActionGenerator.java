package ru.mipt.bit.platformer.game.command.action_generator;

import ru.mipt.bit.platformer.game.entity.Action;
import ru.mipt.bit.platformer.game.entity.GameObject;
import ru.mipt.bit.platformer.game.entity.Level;
import ru.mipt.bit.platformer.game.input.InputToActionMapper;

import java.util.ArrayList;
import java.util.List;

public class PlayerActionGenerator implements ActionGenerator {
    private final InputToActionMapper inputToActionMapper;

    public PlayerActionGenerator(InputToActionMapper inputToActionMapper) {
        this.inputToActionMapper = inputToActionMapper;
    }

    @Override
    public List<Action> getActions(Level level, GameObject gameObject) {
        List<Action> actions = new ArrayList<>();
        var action = inputToActionMapper.getAction();
        if (action != null) {
            actions.add(action);
        }

        return actions;
    }
}
