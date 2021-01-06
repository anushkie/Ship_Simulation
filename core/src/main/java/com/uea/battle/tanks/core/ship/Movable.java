package com.uea.battle.tanks.core.ship;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.uea.battle.tanks.core.screen.wind.Environment;

public interface Movable {

    void update(OrthographicCamera camera, float delta, Environment environment);

    float getX();
    float getY();

    float getAngle();
    float getVelocity();
}
