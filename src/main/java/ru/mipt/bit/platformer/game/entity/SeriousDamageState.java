package ru.mipt.bit.platformer.game.entity;

public class SeriousDamageState extends DamageState {
    public float getMoveSpeedMultiplier() {
        return 1.0f / 3;
    }

    @Override
    public float getShootSpeedMultiplier() {
        return 0;
    }

    @Override
    public DamageState getNextState() {
        return null;
    }
}
