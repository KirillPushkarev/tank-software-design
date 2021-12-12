package ru.mipt.bit.platformer.game.command.action_generator;

import ru.mipt.bit.platformer.game.entity.Action;
import ru.mipt.bit.platformer.game.entity.GameObject;
import ru.mipt.bit.platformer.game.entity.Level;

import java.util.List;

public interface ActionGenerator {
    List<Action> getActions(Level level, GameObject gameObject);
}
