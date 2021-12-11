package ru.mipt.bit.platformer.game.command;

@FunctionalInterface
public interface CommandProducer<T, U> {
    Command produce(T t, U u);
}
