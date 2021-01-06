package com.uea.battle.tanks.core.ship;

import com.badlogic.gdx.math.Polygon;
import com.uea.battle.tanks.core.screen.wind.Environment;

public interface Ship extends Movable, Renderable {

    void move(Environment environment);
    Polygon getBoundingPolygon();
}
