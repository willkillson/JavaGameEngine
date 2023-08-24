package game.entity;

public interface Composable extends Comparable<Composable> {

    void compose();

    boolean isDead();
}
