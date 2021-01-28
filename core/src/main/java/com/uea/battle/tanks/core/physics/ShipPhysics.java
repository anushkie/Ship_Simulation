package com.uea.battle.tanks.core.physics;

import com.uea.battle.tanks.core.screen.wind.Environment;

import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.sin;

public class ShipPhysics {
    public static final float M_PI_2 = 1.57079632679489661923F;
    public static final float M_PI_4 = 0.785F;
    public static final float M_PI = 3.14159265358979323846F;
    public static final float R_EARTH = 63.78000F;

    static void sailing_physics_update(ShipAttributes boat, Environment wind, float dt) {
        if (isSailBounds(boat)) {
            boat.setSheet_length(boat.getSheet_length() + dt * boat.getSail_is_free());
        }

        if (isMainsheetTight(boat, wind)) {
            boat.setSail_angle((float) Math.atan(Math.tan(calculateWindDirection(boat, wind))));

            if (Math.abs(boat.getSail_angle()) != 0) {
                boat.setSheet_length(Math.abs(boat.getSail_angle()));
            }
        } else {
            boat.setSail_angle(signOf(sin(-calculateWindDirection(boat, wind))) * boat.getSheet_length());
        }

        //longitude = x
        boat.setLongitude(boat.getLongitude() + ((deltaX(boat, wind) / R_EARTH) * (180 / M_PI) * dt));
        //latitude = y
        boat.setLatitude(boat.getLatitude() +
                ((deltaY(boat, wind) / R_EARTH) * ((180 / M_PI) / cos(boat.getLatitude() * M_PI / 180)) * dt));

        //set rotational velocity
        boat.setRotational_velocity(boat.getRotational_velocity() + deltaRotationalVelocity(boat, wind) * dt);
        boat.setVelocity(boat.getVelocity() + deltaVelocity(boat, wind) * dt);
        boat.setAngle(boat.getAngle() + boat.getRotational_velocity() * dt);

        //keep angle between 0 and 2*pi

        if (boat.getAngle() < 0) {
            boat.setAngle(boat.getAngle() + M_PI * 2);
        }

        boat.setAngle(boat.getAngle() % (M_PI * 2));
    }

    static float signOf(float a) {
        if (a <= 0) {
            return -1;
        } else {
            return 1;
        }
    }

    static float calculateWindX(ShipAttributes ship, Environment wind) {
        return wind.getWindVelocity() * cos(wind.getWindRadians() - ship.getAngle()) -
                ship.getVelocity();
    }

    static float calculateWindY(ShipAttributes ship, Environment wind) {
        return wind.getWindVelocity() * sin(wind.getWindRadians() - ship.getAngle());
    }

    static float calculateWindDirection(ShipAttributes ship, Environment wind) {
        return (float) Math.atan2(calculateWindY(ship, wind), calculateWindX(ship, wind));
    }

    static float calculateWindSpeed(ShipAttributes boat, Environment wind) {
        return (float) Math.sqrt(Math.pow(calculateWindX(boat, wind), 2) + Math.pow(calculateWindY(boat, wind), 2));
    }

    static boolean isMainsheetTight(ShipAttributes boat, Environment wind) {
        if (cos(calculateWindDirection(boat, wind)) + cos(boat.getSheet_length()) < 0) {
            return true;
        } else {
            return false;
        }
    }

    static float forceOnRudder(ShipAttributes boat, Environment wind) {
        return boat.getRudder_lift() * boat.getVelocity() * sin(boat.getRudder_angle());
    }

    static float forceOnSail(ShipAttributes boat, Environment wind) {
        return boat.getSail_lift() * calculateWindSpeed(boat, wind) * sin(
                boat.getSail_angle() - calculateWindDirection(boat, wind));
    }

    static boolean isSailBounds(ShipAttributes boat) {
        if (boat.getSheet_length() > -M_PI_2 && boat.getSheet_length() < M_PI_2) {
            return true;
        } else {
            return false;
        }
    }

    static float deltaY(ShipAttributes boat, Environment wind) {
        return boat.getVelocity()
                * cos(boat.getAngle())
                + boat.getDrift_coefficient()
                * wind.getWindVelocity()
                * cos(wind.getWindRadians());
    }

    static float deltaX(ShipAttributes boat, Environment wind) {
        return boat.getVelocity()
                * sin(boat.getAngle())
                + boat.getDrift_coefficient()
                * wind.getWindVelocity()
                * sin(wind.getWindRadians());
    }

    static float deltaRotationalVelocity(ShipAttributes boat, Environment wind) {
        return ((boat.getSail_center_of_effort()
                - boat.getMast_distance()
                * cos(boat.getSail_angle()))
                * forceOnSail(boat, wind)
                - boat.getRudder_distance()
                * cos(boat.getRudder_angle())
                * forceOnRudder(boat, wind)
                - boat.getAngular_friction()
                * boat.getRotational_velocity()
                * boat.getVelocity())
                / boat.getInertia();
    }

    static float deltaVelocity(ShipAttributes boat, Environment wind) {
        return (sin(boat.getSail_angle())
                * forceOnSail(boat, wind)
                - sin(boat.getRudder_angle())
                * forceOnRudder(boat, wind)
                - boat.getTangential_friction()
                * boat.getVelocity()
                * boat.getVelocity())
                / boat.getMass();
    }
}
