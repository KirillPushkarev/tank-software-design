package ru.mipt.bit.platformer.game.executor;

import ru.mipt.bit.platformer.common.TriConsumer;
import ru.mipt.bit.platformer.game.entity.Action;
import ru.mipt.bit.platformer.game.entity.ActionType;
import ru.mipt.bit.platformer.game.entity.GameObject;
import ru.mipt.bit.platformer.game.executor.direction_strategy.ActionGenerator;

import java.util.List;
import java.util.Map;

public class CommonActionExecutor implements ActionExecutor {
    private final ActionGenerator actionGenerator;
    private final Map<ActionType, TriConsumer<Action, GameObject, Float>> commandByActionType = Map.of(
            ActionType.MOVE, CommonActionExecutor::move,
            ActionType.SHOOT, CommonActionExecutor::shoot
    );

    public CommonActionExecutor(ActionGenerator actionGenerator) {
        this.actionGenerator = actionGenerator;
    }

    public void executeFor(GameObject gameObject, float deltaTime) {
        List<Action> actions = actionGenerator.getActions(gameObject, deltaTime);
        executeActions(actions, gameObject, deltaTime);
    }

    private void executeActions(List<Action> actions, GameObject gameObject, float deltaTime) {
        for (Action action : actions) {
            var command = commandByActionType.get(action.getActionType());
            command.accept(action, gameObject, deltaTime);
        }
    }

    private static void move(Action action, GameObject gameObject, Float deltaTime) {
        gameObject.move(action.getDirection(), deltaTime);
    }

    private static void shoot(Action action, GameObject gameObject, Float deltaTime) {
    }
}
