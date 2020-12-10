package com.uea.battle.tanks.core.tank;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

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

            case UP: {
                //sprite.translateY(32);
                float newX =  calculateDistanceX(1, sprite.getRotation() % 360);
                float newY = calculateDistanceY(1, sprite.getRotation() % 360);
                sprite.translate(newX, newY);
                break;
            }
            case LEFT:
                //sprite.translateX(-32);
                //float offset = params.getRotVelocity();
                sprite.setRotation(sprite.getRotation() + 1);



                /*if (offset + position.getAngle() >= 360) {
                    float newAngle = (position.getAngle() + offset - 360);
                    params.getBoundingPolygon().setRotation(newAngle);

                    if(!collisionDetector.isColliding(entity)) {
                        position.setAngle(newAngle);
                        moved = true;
                    } else {
                        resetBoundingBoxAngle(params);
                    }
                } else {
                    float newAngle = (position.getAngle() + offset);
                    params.getBoundingPolygon().setRotation(newAngle);

                    if(!collisionDetector.isColliding(entity)) {
                        position.setAngle(newAngle);
                        moved = true;
                    } else {
                        resetBoundingBoxAngle(params);
                    }
                }
*/
                break;
            case RIGHT:
               // sprite.translateX(32);
                sprite.setRotation(sprite.getRotation() - 1);

                break;
            case DOWN:
               // sprite.translateY(-32);
                float newX = calculateDistanceX(1, sprite.getRotation()%360);
                float newY =  calculateDistanceY(1, sprite.getRotation()%360);
                sprite.translate(-newX, -newY);


                //params.getBoundingPolygon().setPosition(newX, newY);

                /*if(!collisionDetector.isColliding(entity)) {
                    position.setX(newX);
                    position.setY(newY);
                    moved = true;
                } else {
                    resetBoundingPolygonPosition(params);
                }*/
                break;
        }
    }

    protected float calculateDistanceX(float velocity, float angle) {
        return velocity * MathUtils.cos(MathUtils.degreesToRadians * angle);
    }

    protected float calculateDistanceY(float velocity, float angle) {
        return velocity * MathUtils.sin(MathUtils.degreesToRadians * angle);
    }


    @Override
    public void update(OrthographicCamera camera, float delta) {

    }
}
