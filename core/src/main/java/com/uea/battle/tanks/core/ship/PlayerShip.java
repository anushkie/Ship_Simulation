package com.uea.battle.tanks.core.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.util.dialog.OptionDialogListener;
import com.uea.battle.tanks.core.map.MapLoader;
import com.uea.battle.tanks.core.screen.wind.Environment;
import com.uea.battle.tanks.core.ui.GameUI;

import java.util.ArrayList;
import java.util.List;

public class PlayerShip implements Ship {

    private static final int VELOCITY = 1;

    int width = 18;
    int height = 40;
    float hWidth = width / 2f;
    float hHeight = height / 2f;
    float backWidth = hWidth / 2f;
    float fronIndent = height / 3.2f;
    float backIndent = height - fronIndent;
    float backBulgeIndent = backIndent * (1 - 1 / 2.3f);
    float frontBulgeIndent = backWidth * 1.7f;
    float sailLength = backIndent * 0.9f;
    float sailWidth = width * 0.1f;
    float mastsD = sailWidth * 1.3f;
    float backBulge = backIndent * 1.075f;
    float rudderLength = 10f;
    float arrowSize = 10f;

    private final Sprite boatSprite, rudderSprite, sailSprite, windSprite;
    private final MapLoader mapManager;
    private final Polygon polygon;
    private final List<Runnable> spritePositionChangeListeners = new ArrayList<>();
    private Stage stage;
    private GameUI gameUI;
    Vector boat, boatDirection, rudder, sail, wind;
    float lateralResistance = 0.0f;
    double simulationSpeed = 50;
    double stepAngleChangeRadian = 0.0872664626;
    double rudderForce = 0.01;
    double prevBoatDirection;
    int countCollided = 1;

    public PlayerShip(Sprite boatSprite, MapLoader mapManager, Stage stage) {
        this.boatSprite = boatSprite;
        this.rudderSprite = new Sprite(new Texture("ui/rudder.png"));
        this.sailSprite = new Sprite(new Texture("ui/ship_sail.png"));
        this.windSprite = new Sprite(new Texture("ui/Tank-Turret.png"));
        this.boatSprite.setPosition(0, 0);
        this.rudderSprite.setPosition(0, 0);
        this.sailSprite.setPosition(0, 0);
        this.windSprite.setPosition(0, 0);
        this.stage = stage;
        this.mapManager = mapManager;
        this.polygon = createBoundingPolygon();
        boat = new Vector(640, 640);
        boatDirection = new Vector(0, -1);
        boatDirection.calcAngle();
        prevBoatDirection = boatDirection.getAngle();
        wind = new Vector(0, 0);
        wind.setLength(1);
        wind.setAngle(Math.PI / 4);
        sail = new Vector(1, 0);
        rudder = new Vector(-rudderLength, 0);
        rudder.calcLength();
        rudder.calcAngle();
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        boatSprite.draw(spriteBatch);

        rudderSprite.draw(spriteBatch);

        sailSprite.setColor(Color.BLACK);
        sailSprite.draw(spriteBatch);

        windSprite.setColor(Color.BLUE);
        windSprite.draw(spriteBatch);
    }

    @Override
    public void move(Environment environment) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            sail.setAngle(sail.getAngle() + 0.0872664626);
            spritePositionChangeListeners.forEach(Runnable::run);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            sail.setAngle(sail.getAngle() - 0.0872664626);
            spritePositionChangeListeners.forEach(Runnable::run);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            if (rudder.getAngle() > 0.75 * Math.PI)
                rudder.setAngle(rudder.getAngle() - stepAngleChangeRadian);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            if (rudder.getAngle() < 1.25 * Math.PI)
                rudder.setAngle(rudder.getAngle() + stepAngleChangeRadian);

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            rudder.setAngle(Math.PI);
        }
    }

    private float calculateAngle(int rotationVelocity) {
        return 0;
    }

    private void resetBoundingPolygonPosition() {
        polygon.setPosition(boatSprite.getX(), boatSprite.getY());
        polygon.setRotation(boatSprite.getRotation());
    }

    private float calculateDistanceX(float velocity, float angle, Environment environment) {
        return velocity * MathUtils.cos(MathUtils.degreesToRadians * angle);
    }

    private float calculateDistanceY(float velocity, float angle, Environment environment) {
        return velocity * MathUtils.sin(MathUtils.degreesToRadians * angle);
    }

    @Override
    public void update(OrthographicCamera camera, float delta, Environment environment) {
        if(mapManager.isColliding(this)) {
            System.out.println("colliding"); //, update last valid pos, return
            Dialogs.showOptionDialog(stage, "!!COLLISION - ALERT!!"+countCollided+ "/3", "Do you want to continue?", Dialogs.OptionDialogType.YES_NO, new OptionDialogListener() {
                @Override
                public void yes() {
                    sailSprite.setPosition(23, 70);
                    rudderSprite.setPosition(23, 70);
                    boatSprite.setPosition(23, 70);
                    windSprite.setPosition(23, 70);
                }

                @Override
                public void no() {
                    Gdx.app.exit();
                }

                @Override
                public void cancel() {

                }
            });
        }
        countCollided++;
        Vector f = wind.projection(sail);
        Vector v = f.projection(boatDirection);
        Vector d = Vector.multiply(lateralResistance, (Vector.minus(f, v)));
        Vector vPlusD = Vector.plus(v, d);
        Vector deltaIntoVPlusD = Vector.multiply(delta, vPlusD);
        Vector simSpeedMultipliedByDeltaIntoVPlusD = Vector.multiply(simulationSpeed, deltaIntoVPlusD);
        boat = Vector.plus(boat, simSpeedMultipliedByDeltaIntoVPlusD);
        v.calcLength();
        boatDirection.setAngle(boatDirection.getAngle() + Vector.signCos(f, boatDirection) * rudderForce * v.getLength() * rudder.y / rudderLength);
        sail.setAngle(sail.getAngle() + boatDirection.getAngle() - prevBoatDirection);

        boatSprite.setPosition((float) boat.x, (float) boat.y);
        boatSprite.setRotation(boatDirection.getAngleDegree());

        polygon.setPosition((float) boat.x, (float) boat.y);
        polygon.setRotation(boatDirection.getAngleDegree());

        rudderSprite.setPosition((float) boat.x, (float) boat.y - rudderLength);
        rudderSprite.setRotation((float) (rudder.getAngleDegree() + boatDirection.getAngleDegree() - MathUtils.radDeg * prevBoatDirection));

        sailSprite.setPosition((float) boat.x, (float) boat.y);
        sailSprite.setRotation(sail.getAngleDegree() - boatDirection.getAngleDegree());

        spritePositionChangeListeners.forEach(Runnable::run);

        windSprite.setPosition((float) boat.x, (float) boat.y);
        windSprite.setRotation(wind.getAngleDegree());

        prevBoatDirection = boatDirection.getAngle();

        /*if (MathUtils.random(1000) == 1) {
            wind.setAngle(MathUtils.random(360) * MathUtils.degreesToRadians);
        }*/
        environment.setWindRadians((float) wind.getAngle());
    }

    @Override
    public float getX() {
        return boatSprite.getX();
    }

    @Override
    public float getY() {
        return boatSprite.getY();
    }


    @Override
    public float getAngle() {
        return boatSprite.getRotation();
    }

    @Override
    public float getVelocity() {
        return VELOCITY;
    }

    public float getRudderAngle() {
        return rudder.getAngleDegree();
    }

    public float getSailAngle() {
        return sail.getAngleDegree();
    }

    @Override
    public Polygon getBoundingPolygon() {
        return polygon;
    }

    public void registerPositionCallback(Runnable positionChangeListener) {
        this.spritePositionChangeListeners.add(positionChangeListener);
    }

    private Polygon createBoundingPolygon() {
        //This polygon well is created by the internet.! DO NOT CHANGE IT
        Polygon polygon = new Polygon(new float[]{
                27, 58, 12, 60, 6, 53, 4, 45, 7, 43, 8, 23, 5, 21, 5, 12, 11, 9, 26, 7, 31, 13, 37, 14, 42, 8, 60, 8, 64, 17, 57, 21, 53, 27, 54, 38, 58, 45, 64, 50, 61, 57, 43, 60, 35, 51
        });

        polygon.setOrigin(boatSprite.getOriginX(), boatSprite.getOriginY());
        polygon.setPosition(boatSprite.getX(), boatSprite.getY());
        polygon.setRotation(boatSprite.getRotation());

        return polygon;
    }

    public void acceleratorPressed() {
        System.out.println("Accelerator pressed");

    }

    public void reverseAcceleratorPressed() {
        System.out.println("decelrator pressed");
    }

    public void acceleratorReleased() {
        System.out.println("Accelerator released");
    }

    private enum AccelerationDirection {
        FORWARDS,
        BACKWARDS
    }
}
