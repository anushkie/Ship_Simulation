package com.uea.battle.tanks.core.tank;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerShip implements Ship {

    private final Sprite sprite;

    public PlayerShip(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }

    @Override
    public void move(Direction direction) {
        switch (direction) {

            case UP:
                sprite.translateY(32);
                break;
            case LEFT:
                sprite.translateX(-32);
                break;
            case RIGHT:
                sprite.translateX(32);
                break;
            case DOWN:
                sprite.translateY(-32);
                break;
        }
    }

    @Override
    public void update(OrthographicCamera camera, float delta) {

    }
}
