package ru.mipt.bit.platformer.game.command;

import ru.mipt.bit.platformer.game.entity.Action;
import ru.mipt.bit.platformer.game.entity.CommandType;
import ru.mipt.bit.platformer.game.entity.GameObject;
import ru.mipt.bit.platformer.game.command.action_generator.ActionGenerator;

import java.util.List;
import java.util.Map;

public class CommandExecutor {
    private final ActionGenerator actionGenerator;
    private final Map<CommandType, CommandProducer<Action, GameObject>> commandProducerByActionType = Map.of(
            CommandType.MOVE, this::move,
            CommandType.SHOOT, this::shoot
    );

    public CommandExecutor(ActionGenerator actionGenerator) {
        this.actionGenerator = actionGenerator;
    }

    public void executeFor(GameObject gameObject) {
        List<Action> actions = actionGenerator.getActions(gameObject);
        executeCommands(actions, gameObject);
    }

    private void executeCommands(List<Action> actions, GameObject gameObject) {
        for (Action action : actions) {
            var commandProducer = commandProducerByActionType.get(action.getActionType());
            var command = commandProducer.produce(action, gameObject);
            command.execute();
        }
    }

    private MoveCommand move(Action action, GameObject gameObject) {
        return new MoveCommand(gameObject, action.getDirection());
    }

    private EmptyCommand shoot(Action action, GameObject gameObject) {
        return new EmptyCommand();
    }
}
