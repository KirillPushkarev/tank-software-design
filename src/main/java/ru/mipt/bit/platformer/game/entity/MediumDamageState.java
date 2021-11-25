package ru.mipt.bit.platformer.game.entity;

public class MediumDamageState extends DamageState{
    public float getMoveSpeedMultiplier() {
        return 1.0f / 2;
    }

    @Override
    public float getShootSpeedMultiplier() {
        return 1;
    }

    @Override
    public DamageState getNextState() {
        return new SeriousDamageState();
    }
}
