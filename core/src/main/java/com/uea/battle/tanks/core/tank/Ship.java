package com.uea.battle.tanks.core.tank;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Ship {

    void render(SpriteBatch spriteBatch);

    void move(Direction direction);

    void update(OrthographicCamera camera, float delta);

    float getX();
    float getY();
}
