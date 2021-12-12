package ru.mipt.bit.platformer.game.command;

import ru.mipt.bit.platformer.game.entity.Action;
import ru.mipt.bit.platformer.game.entity.ActionType;
import ru.mipt.bit.platformer.game.entity.GameObject;
import ru.mipt.bit.platformer.game.command.action_generator.ActionGenerator;
import ru.mipt.bit.platformer.game.entity.Level;

import java.util.List;
import java.util.Map;

public class CommandExecutor {
    private final ActionGenerator actionGenerator;
    private final Map<ActionType, CommandProducer<Action, GameObject>> commandProducerByActionType = Map.of(
            ActionType.MOVE, this::move,
            ActionType.SHOOT, this::shoot
    );

    public CommandExecutor(ActionGenerator actionGenerator) {
        this.actionGenerator = actionGenerator;
    }

    public void executeFor(Level level, GameObject gameObject) {
        List<Action> actions = actionGenerator.getActions(level, gameObject);
        executeCommands(actions, gameObject);
    }

    private void executeCommands(List<Action> actions, GameObject gameObject) {
        for (Action action : actions) {
            var commandProducer = commandProducerByActionType.get(action.getActionType());
            var command = commandProducer.produce(action, gameObject);
            command.execute();
        }
    }

    private Command move(Action action, GameObject gameObject) {
        return new MoveCommand(gameObject, action.getDirection());
    }

    private Command shoot(Action action, GameObject gameObject) {
        return new ShootCommand(gameObject);
    }
}
