package ru.mipt.bit.platformer.game.input;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.game.entity.Action;
import ru.mipt.bit.platformer.game.entity.ActionType;
import ru.mipt.bit.platformer.game.entity.Direction;

import static com.badlogic.gdx.Input.Keys.*;

public class InputToActionMapper {
    public Action getAction() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return new Action(ActionType.MOVE, Direction.UP);
        }

        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return new Action(ActionType.MOVE, Direction.LEFT);
        }

        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return new Action(ActionType.MOVE, Direction.DOWN);
        }

        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return new Action(ActionType.MOVE, Direction.RIGHT);
        }

        if (Gdx.input.isKeyPressed(SPACE)) {
            return new Action(ActionType.SHOOT);
        }

        return null;
    }
}
