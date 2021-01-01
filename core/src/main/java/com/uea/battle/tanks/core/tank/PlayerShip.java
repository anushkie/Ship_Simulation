package com.uea.battle.tanks.core.tank;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.uea.battle.tanks.core.map.MapLoader;

public class PlayerShip implements Ship {

    private final Sprite sprite;
    private final MapLoader mapManager;
    private final Polygon polygon;

    public PlayerShip(Sprite sprite, MapLoader mapManager) {
        this.sprite = sprite;
        this.mapManager = mapManager;
        this.polygon = getTankPolygon();
    }

    @Override
    //Attempting to get the position of the docks at the bottom of the screen
    //Will then use the commented line to generate them in this area
    public void render(SpriteBatch spriteBatch) {
//        sprite.draw(spriteBatch, position);
        sprite.draw(spriteBatch);
    }

    //This method calls "getVertices" which produces a float array of length 3, with x, y and angle values
    //Still unsure if we should make it a part of the object to be initialised in the constructor?
    public float[] getPosition(Sprite sprite){
        return sprite.getVertices();
    }

    @Override
    public void move(Direction direction) {
        switch (direction) {

            case UP: {
                float newX =  calculateDistanceX(1, sprite.getRotation() % 360);
                float newY = calculateDistanceY(1, sprite.getRotation() % 360);

                polygon.translate(newX, newY);

                if(!mapManager.isColliding(this)) {
                    sprite.translate(newX, newY);
                } else {
                    resetBoundingPolygonPosition();
                }
                break;
            }

            case LEFT: {
                polygon.setRotation(polygon.getRotation() + 1);
                if(!mapManager.isColliding(this)) {
                    sprite.setRotation(sprite.getRotation() + 1);
                } else {
                    resetBoundingPolygonPosition();
                }
                break;
            }

            case RIGHT: {
                polygon.setRotation(polygon.getRotation() - 1);

                if(!mapManager.isColliding(this)) {
                    sprite.setRotation(sprite.getRotation() - 1);
                } else {
                    resetBoundingPolygonPosition();
                }
                break;
            }

            case DOWN: {
                float newX = calculateDistanceX(1, sprite.getRotation()%360);
                float newY =  calculateDistanceY(1, sprite.getRotation()%360);

                //sprite.translate(-newX, -newY);
                polygon.translate(-newX, -newY);

                if(!mapManager.isColliding(this)) {
                    sprite.translate(-newX, -newY);
                    polygon.setRotation(sprite.getRotation());
                } else {
                    resetBoundingPolygonPosition();
                }
                break;
            }
        }
    }

    private void resetBoundingPolygonPosition() {
        polygon.setPosition(sprite.getX(), sprite.getY());
        polygon.setRotation(sprite.getRotation());
    }

    protected float calculateDistanceX(float velocity, float angle) {
        return velocity * MathUtils.cos(MathUtils.degreesToRadians * angle);
    }

    protected float calculateDistanceY(float velocity, float angle) {
        return velocity * MathUtils.sin(MathUtils.degreesToRadians * angle);
    }

    
    public float[] distance(Sprite player, Sprite enemyShip) {
        float[] dis = new float[3];
        float[] playerPos = player.getVertices();
        float[] enemyPos = enemyShip.getVertices();
        dis[0] = (playerPos[0] = enemyPos[0]);
        dis[1] = (playerPos[1] = enemyPos[1]);
        dis[2] = (playerPos[2] = enemyPos[2]);
        return dis;
    }


    @Override
    public void update(OrthographicCamera camera, float delta) {

    }

    @Override
    public float getX() {
        return sprite.getX();
    }

    @Override
    public float getY() {
        return sprite.getY();
    }

    @Override
    public Polygon getBoundingPolygon() {
        return polygon;
    }

    public Polygon getTankPolygon() {
        //This polygon well is created by the internet.! DO NOT CHANGE IT
        Polygon polygon = new Polygon(new float[]{
                27,58,12,60,6,53,4,45,7,43,8,23,5,21,5,12,11,9,26,7,31,13,37,14,42,8,60,8,64,17,57,21,53,27,54,38,58,45,64,50,61,57,43,60,35,51
        });

        polygon.setOrigin(sprite.getOriginX(), sprite.getOriginY());
        polygon.setPosition(sprite.getX(), sprite.getY());
        polygon.setRotation(sprite.getRotation());

        return polygon;
    }
}
