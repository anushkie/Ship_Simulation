package com.uea.battle.tanks.core.movement;

enum Direction{
    UP,
    DOWN,
    LEFT,
    RIGHT
}
public class Move {

    Direction direction;

    public Move(Direction direction) {
        this.direction = direction;
    }

    public static void move(Direction direction) {
        switch (direction){
            case UP:
        }
    }
}

