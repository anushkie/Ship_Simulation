package com.uea.battle.tanks.core.tank;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;

public class EnemyShip implements Ship {

    private final Sprite sprite;

    public EnemyShip(Sprite sprite) {
        this.sprite = sprite;
        this.sprite.setPosition(MathUtils.random(100), MathUtils.random(100));
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }


    @Override
    public void move(Direction direction) {

    }

    @Override
    public void update(OrthographicCamera camera, float delta) {

    }

    @Override
    public Polygon getBoundingPolygon() {
        return null;
    }

    @Override
    public float getX() {
        return sprite.getX();
    }

    @Override
    public float getY() {
        return sprite.getY();
    }
}
