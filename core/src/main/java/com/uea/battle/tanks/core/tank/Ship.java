package com.uea.battle.tanks.core.tank;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;

public interface Ship {

    void render(SpriteBatch spriteBatch);

    void move(Direction direction);

    void update(OrthographicCamera camera, float delta);

    float getX();
    float getY();

    Polygon getBoundingPolygon();
}
