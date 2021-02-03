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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.util.dialog.OptionDialogListener;
import com.kotcrab.vis.ui.widget.ButtonBar;
import com.kotcrab.vis.ui.widget.VisDialog;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.uea.battle.tanks.core.map.MapLoader;
import com.uea.battle.tanks.core.screen.environment.Environment;

import java.util.ArrayList;
import java.util.List;

public class PlayerShip implements Ship {

    private static final int VELOCITY = 1;
    public static final float M_PI_2 = 1.57079632679489661923F;

    float rudderLength = 10f;

    private final Sprite boatSprite, rudderSprite, sailSprite;
    private final MapLoader mapManager;
    private Polygon polygon;
    private final List<Runnable> spritePositionChangeListeners = new ArrayList<>();
    private final Stage stage;
    Vector boat, boatDirection, rudder, sail, wind;
    float lateralResistance = 0.0f;
    double simulationSpeed = 50;
    double stepAngleChangeRadian = 0.0872664626;
    double rudderForce = 0.01;
    double prevBoatDirection;
    double prevShipPosX;
    double prevShipPosY;
    int countCollided = 0;
    boolean isCrashed = false;

    public PlayerShip(Sprite boatSprite, MapLoader mapManager, Stage stage) {
        this.boatSprite = boatSprite;
        this.rudderSprite = new Sprite(new Texture("ui/rudder.png"));
        this.sailSprite = new Sprite(new Texture("ui/ship_sail.png"));
        this.stage = stage;
        this.mapManager = mapManager;
        resetBoat();
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        boatSprite.draw(spriteBatch);

        rudderSprite.draw(spriteBatch);

        sailSprite.setColor(Color.BLACK);
        sailSprite.draw(spriteBatch);

        //shapeRenderer.polygon(polygon.getTransformedVertices());
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
    
    @Override
    public void update(OrthographicCamera camera, float delta, Environment environment) {
        if (mapManager.isColliding(this)) {
            if (isCrashed) {
                return;
            }
            countCollided++;

            isCrashed = true;
            System.out.println("colliding"); //, update last valid pos, return

            System.out.println("boat X pos: " + boatSprite.getX());
            System.out.println("boat Y pos: " + boatSprite.getY());

            if (countCollided == 3) {
                showGameOverDialog();
            } else {
                showOptionDialog();
            }
        }

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

        rudderSprite.setPosition((float) boat.x + 23, (float) boat.y - 8 - rudderLength);
        rudderSprite.setRotation((float) (rudder.getAngleDegree() + boatDirection.getAngleDegree() - MathUtils.radDeg * prevBoatDirection));
        //rudderSprite.setRotation((float) (rudder.getAngleDegree() - boatDirection.getAngleDegree()));

        sailSprite.setPosition((float) boat.x + 20, (float) boat.y);
        sailSprite.setRotation(sail.getAngleDegree() - boatDirection.getAngleDegree());

        spritePositionChangeListeners.forEach(Runnable::run);

        wind.getAngleDegree();

        prevBoatDirection = boatDirection.getAngle();

        if (!isCrashed) {
            prevShipPosX = boat.x;
            prevShipPosY = boat.y;
        }


        if (MathUtils.random(1000) == 1) {
            wind.setAngle(MathUtils.random(360) * MathUtils.degreesToRadians);
        }
        environment.setWindRadians((float) wind.getAngle());
    }

    private void showOptionDialog() {
        Dialogs.showOptionDialog(stage, "!!COLLISION - ALERT!!" + countCollided + "/3", "Do you want to continue?", Dialogs.OptionDialogType.YES_NO, new OptionDialogListener() {

            @Override
            public void yes() {
                isCrashed = false;

                resetBoat();
                // todo randomise wind here
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

    private void showGameOverDialog() {
        final VisDialog dialog = new VisDialog("Game Over") {
            @Override
            public void setObject(Actor actor, Object object) {
                if (actor instanceof VisTextButton) {
                    ((VisTextButton) object).addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            Gdx.app.exit();
                        }
                    });
                }
                super.setObject(actor, object);
            }
        };
        dialog.closeOnEscape();
        dialog.text("Game is over");
        dialog.button(ButtonBar.ButtonType.OK.getText()).padBottom(3);
        dialog.pack();
        dialog.centerWindow();
        dialog.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    dialog.fadeOut();
                    return true;
                }
                return false;
            }
        });
        stage.addActor(dialog.fadeIn());
    }

    private void resetBoat() {
        this.boatSprite.setX(0);
        this.boatSprite.setY(0);
        this.rudderSprite.setPosition(0, 0);
        this.sailSprite.setPosition(0, 0);
        this.boat = new Vector(640, 640);
        this.boatDirection = new Vector(0, -1);
        this.boatDirection.calcAngle();
        this.prevBoatDirection = boatDirection.getAngle();
        this.wind = new Vector(0, 0);
        this.wind.setLength(1);
        this.wind.setAngle(Math.PI / 4);
        this.sail = new Vector(1, 0);
        this.rudder = new Vector(-rudderLength, 0);
        this.rudder.calcLength();
        this.rudder.calcAngle();
        this.polygon = createBoundingPolygon();
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
        Polygon polygon = new Polygon(new float[]{
                0, 0,
                53, 0,
                53, 30,
                0, 30
        });

        polygon.setOrigin(boatSprite.getOriginX(), boatSprite.getOriginY());
        polygon.setPosition(boatSprite.getX(), boatSprite.getY());
        polygon.setRotation(boatSprite.getRotation());

        return polygon;
    }
}
