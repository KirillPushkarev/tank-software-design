package ru.mipt.bit.platformer.game.entity;

public abstract class DamageState {
    public abstract float getMoveSpeedMultiplier();
    public abstract float getShootSpeedMultiplier();
    public abstract DamageState getNextState();
}
