package com.uea.battle.tanks.core.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.uea.battle.tanks.core.ship.PlayerShip;

public class GameUI {

    private static final String WIND_DIRECTION_FORMAT = "Wind Direction : %s";
    private static final String WIND_SPEED_FORMAT = "Wind Velocity     : %s M/S";
    private static final String SHIP_SPEED_FORMAT = "Ship Velocity     : %s M/S";
    private static final String SHIP_ANGLE_FORMAT = "Ship Angle     : %s Degrees";
    private static final String RUDDER_ANGLE_FORMAT = "Rudder Angle     : %s Degrees";
    private static final String SAIL_ANGLE_FORMAT = "Sail Angle     : %s Degrees";
    private static final String SHIP_X_FORMAT = "Ship X     : %s";
    private static final String SHIP_Y_FORMAT = "Ship Y     : %s";
    private static final String CARGO_FORMAT = "Collected %s of %s Cargos";
    private static final String CARGO_WEIGHT_ON_SHIP = "Ship Weight  : %s Pounds";

    private VisLabel directionLabel;
    private VisLabel windSpeedLabel;
    private VisLabel shipSpeedLabel;

    private VisLabel cargoLabel;

    public Stage getStage() {
        return cargoWeightOnShip.getStage();
    }

    private VisLabel cargoWeightOnShip;


    public GameUI(Stage stage, PlayerShip playerShip) {
        addWindInformation(stage);
        addCargoInformation(stage);
        addShipInformation(stage, playerShip);
    }

    private void addCargoInformation(Stage stage) {
        VisTable cargoRootContainer = new VisTable();
        cargoRootContainer.setFillParent(true);
        cargoRootContainer.bottom().right();

        VisTable cargoContent = new VisTable();

        cargoLabel = new VisLabel(String.format(CARGO_FORMAT, "0", "0"));
        cargoWeightOnShip = new VisLabel(String.format(CARGO_WEIGHT_ON_SHIP, "1000" ));

        cargoContent.add(cargoLabel).row();
        cargoContent.add(cargoLabel);
        cargoContent.add(cargoWeightOnShip).row();
        cargoContent.add(cargoWeightOnShip);

        cargoRootContainer.add(cargoContent);
        stage.addActor(cargoRootContainer);
    }

    private void addShipInformation(Stage stage, PlayerShip playerShip) {
        VisTable shipRootContainer = new VisTable();
        shipRootContainer.setFillParent(true);
        shipRootContainer.top().left();

        VisTable shipContent = new VisTable();

        shipSpeedLabel = new VisLabel(String.format(SHIP_SPEED_FORMAT, playerShip.getVelocity()));
        VisLabel shipAngleLabel = new VisLabel(String.format(SHIP_ANGLE_FORMAT, playerShip.getAngle()));
        VisLabel rudderAngleLabel = new VisLabel(String.format(RUDDER_ANGLE_FORMAT, playerShip.getRudderAngle()));
        VisLabel sailAngleLabel = new VisLabel(String.format(SAIL_ANGLE_FORMAT, playerShip.getSailAngle()));
        VisLabel shipXLabel = new VisLabel(String.format(SHIP_X_FORMAT, playerShip.getX()));
        VisLabel shipYLabel = new VisLabel(String.format(SHIP_Y_FORMAT, playerShip.getY()));
        sailAngleLabel.setColor(Color.BLUE);
        rudderAngleLabel.setColor(Color.GREEN);

        playerShip.registerPositionCallback(new Runnable() {
            @Override
            public void run() {
                shipAngleLabel.setText(String.format(SHIP_ANGLE_FORMAT, playerShip.getAngle()));
                rudderAngleLabel.setText(String.format(RUDDER_ANGLE_FORMAT, playerShip.getRudderAngle()));
                sailAngleLabel.setText(String.format(SAIL_ANGLE_FORMAT, playerShip.getSailAngle()));
                shipXLabel.setText(String.format(SHIP_X_FORMAT, playerShip.getX()));
                shipYLabel.setText(String.format(SHIP_Y_FORMAT, playerShip.getY()));
            }
        });

        shipContent.add(shipSpeedLabel).row();
        shipContent.add(shipAngleLabel).row();
        shipContent.add(rudderAngleLabel).row();
        shipContent.add(sailAngleLabel).row();
        shipContent.add(shipXLabel).row();
        shipContent.add(shipYLabel).row();

        shipRootContainer.add(shipContent);

        stage.addActor(shipRootContainer);
    }

    private void addWindInformation(Stage stage) {
        VisTable windRootContainer = new VisTable();
        windRootContainer.setFillParent(true);
        windRootContainer.top().right();

        VisTable windContent = new VisTable();

        directionLabel = new VisLabel(String.format(WIND_DIRECTION_FORMAT, "None"));
        windSpeedLabel = new VisLabel(String.format(WIND_SPEED_FORMAT, 0));

        windContent.add(directionLabel).row();
        windContent.add(windSpeedLabel);

        windRootContainer.add(windContent);

        stage.addActor(windRootContainer);
    }

    public void updateSpeedAndDirection(String windDirection, float velocity) {
        directionLabel.setText(String.format(WIND_DIRECTION_FORMAT, windDirection));
        windSpeedLabel.setText(String.format(WIND_SPEED_FORMAT, velocity));
    }

    public void updateCollectedCargoCount(int totalCargos, int collectedCargos, int cargoWeight) {
        cargoLabel.setText(String.format(CARGO_FORMAT, totalCargos, collectedCargos));
        if(cargoWeight >= 1750)
            cargoWeightOnShip.setText(String.format(CARGO_WEIGHT_ON_SHIP, 1000));
        else
            cargoWeightOnShip.setText(String.format(CARGO_WEIGHT_ON_SHIP,cargoWeight));


        if(cargoWeight >= 1750) {
            Dialogs.showOKDialog(cargoLabel.getStage(), "Ship cannot carry more weight", "Do not collect more cargos");
        }

    }
}
