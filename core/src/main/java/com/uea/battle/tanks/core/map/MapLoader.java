package com.uea.battle.tanks.core.map;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.uea.battle.tanks.core.tank.PlayerShip;

import java.util.ArrayList;
import java.util.List;

public class MapLoader {

    /**
     * The polygon list to hold collision polygons
     **/
    private static final List<Polygon> collisionPolygons = new ArrayList<>();

    public void setUpMap(Map map) {
        collisionPolygons.clear();

        MapObjects mapObjects = map.getLayers().get("Collisions").getObjects();

        for (MapObject object : mapObjects) {
            if (object instanceof RectangleMapObject) {
                Rectangle r = ((RectangleMapObject) object).getRectangle();
                Polygon rPoly = new Polygon(new float[]{0, 0, r.width, 0, r.width,
                        r.height, 0, r.height});
                rPoly.setPosition(r.x, r.y);
                collisionPolygons.add(rPoly);
            }
        }
    }

    public boolean isColliding(PlayerShip ship) {
        return collisionPolygons.stream().anyMatch(x -> Intersector.overlapConvexPolygons(x, ship.getBoundingPolygon()));
    }
}
