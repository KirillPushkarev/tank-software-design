package ru.mipt.bit.platformer.game;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class ProgressCalculator {
    public float continueProgress(float previousProgress, float deltaTime, float speed) {
        return clamp(previousProgress + deltaTime / speed, 0f, 1f);
    }
}
