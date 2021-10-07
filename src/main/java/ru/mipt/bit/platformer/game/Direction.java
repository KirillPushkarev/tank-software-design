package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    NONE {
        @Override
        public float getRotation() {
            return 0f;
        }

        @Override
        public GridPoint2 getNextCoordinates(GridPoint2 coordinates) {
            return coordinates;
        }
    },
    UP {
        @Override
        public float getRotation() {
            return 90f;
        }

        @Override
        public GridPoint2 getNextCoordinates(GridPoint2 coordinates) {
            return incrementedY(coordinates);
        }
    },
    DOWN {
        @Override
        public float getRotation() {
            return -90f;
        }

        @Override
        public GridPoint2 getNextCoordinates(GridPoint2 coordinates) {
            return decrementedY(coordinates);
        }
    },
    LEFT {
        @Override
        public float getRotation() {
            return -180f;
        }

        @Override
        public GridPoint2 getNextCoordinates(GridPoint2 coordinates) {
            return decrementedX(coordinates);
        }
    },
    RIGHT {
        @Override
        public float getRotation() {
            return 0f;
        }

        @Override
        public GridPoint2 getNextCoordinates(GridPoint2 coordinates) {
            return incrementedX(coordinates);
        }
    };

    public abstract float getRotation();

    public abstract GridPoint2 getNextCoordinates(GridPoint2 coordinates);

    public static GridPoint2 incrementedY(GridPoint2 point) {
        return new GridPoint2(point).add(0, 1);
    }

    public static GridPoint2 decrementedX(GridPoint2 point) {
        return new GridPoint2(point).sub(1, 0);
    }

    public static GridPoint2 decrementedY(GridPoint2 point) {
        return new GridPoint2(point).sub(0, 1);
    }

    public static GridPoint2 incrementedX(GridPoint2 point) {
        return new GridPoint2(point).add(1, 0);
    }
}
