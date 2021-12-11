package ru.mipt.bit.platformer.game.entity;

public class NoDamageState extends  DamageState {
    public float getMoveSpeedMultiplier() {
        return 1;
    }

    @Override
    public float getShootSpeedMultiplier() {
        return 1;
    }

    @Override
    public DamageState getNextState() {
        return new MediumDamageState();
    }
}
