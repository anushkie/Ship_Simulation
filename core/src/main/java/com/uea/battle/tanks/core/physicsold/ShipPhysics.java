package com.uea.battle.tanks.core.physicsold;

import com.uea.battle.tanks.core.screen.wind.Environment;

import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.sin;

public class ShipPhysics {
    public static final float M_PI_2 = 1.57079632679489661923F;
    public static final float M_PI_4 = 0.785F;
    public static final float M_PI = 3.14159265358979323846F;
    public static final float R_EARTH = 63.78000F;

    static void sailing_physics_update(ShipAttributes boat, Environment wind, float dt) {
        if (sail_is_bounds(boat)) {
            boat.setSheet_length(boat.getSheet_length() + dt * boat.getSail_is_free());
        }

        if (mainsheet_is_tight(boat, wind)) {
            boat.setSail_angle((float) Math.atan(Math.tan(apparent_wind_direction(boat, wind))));

            if (Math.abs(boat.getSail_angle()) != 0) {
                boat.setSheet_length(Math.abs(boat.getSail_angle()));
            }
        } else {
            boat.setSail_angle(sign_of(sin(-apparent_wind_direction(boat, wind))) * boat.getSheet_length());
        }

        //longitude = x
        boat.setLongitude(boat.getLongitude() + ((delta_x(boat, wind) / R_EARTH) * (180 / M_PI) * dt));
        //latitude = y
        boat.setLatitude(boat.getLatitude() +
                ((delta_y(boat, wind) / R_EARTH) * ((180 / M_PI) / cos(boat.getLatitude() * M_PI / 180)) * dt));

        //set rotational velocity
        boat.setRotational_velocity(boat.getRotational_velocity() + delta_rotational_velocity(boat, wind) * dt);
        boat.setVelocity(boat.getVelocity() + delta_velocity(boat, wind) * dt);
        boat.setAngle(boat.getAngle() + boat.getRotational_velocity() * dt);

        //keep angle between 0 and 2*pi

        if (boat.getAngle() < 0) {
            boat.setAngle(boat.getAngle() + M_PI * 2);
        }

        boat.setAngle(boat.getAngle() % (M_PI * 2));
    }

    static float sign_of(float a) {
        if (a <= 0) {
            return -1;
        } else {
            return 1;
        }
    }

    static float apparent_wind_x(ShipAttributes boat, Environment wind) {
        return wind.getWindVelocity() * cos(wind.getWindRadians() - boat.getAngle()) -
                boat.getVelocity();
    }

    static float apparent_wind_y(ShipAttributes boat, Environment wind) {
        return wind.getWindVelocity() * sin(wind.getWindRadians() - boat.getAngle());
    }

    static float apparent_wind_direction(ShipAttributes boat, Environment wind) {
        return (float) Math.atan2(apparent_wind_y(boat, wind), apparent_wind_x(boat, wind));
    }

    static float apparent_wind_speed(ShipAttributes boat, Environment wind) {
        return (float) Math.sqrt(Math.pow(apparent_wind_x(boat, wind), 2) + Math.pow(apparent_wind_y(boat, wind), 2));
    }

    static boolean mainsheet_is_tight(ShipAttributes boat, Environment wind) {
        if (cos(apparent_wind_direction(boat, wind)) + cos(boat.getSheet_length()) < 0) {
            return true;
        } else {
            return false;
        }
    }

    static float force_on_rudder(ShipAttributes boat, Environment wind) {
        return boat.getRudder_lift() * boat.getVelocity() * sin(boat.getRudder_angle());
    }

    static float force_on_sail(ShipAttributes boat, Environment wind) {
        return boat.getSail_lift() * apparent_wind_speed(boat, wind) * sin(
                boat.getSail_angle() - apparent_wind_direction(boat, wind));
    }

    static boolean sail_is_bounds(ShipAttributes boat) {
        if (boat.getSheet_length() > -M_PI_2 && boat.getSheet_length() < M_PI_2) {
            return true;
        } else {
            return false;
        }
    }

    static float delta_y(ShipAttributes boat, Environment wind) {
        return boat.getVelocity()
                * cos(boat.getAngle())
                + boat.getDrift_coefficient()
                * wind.getWindVelocity()
                * cos(wind.getWindRadians());
    }

    static float delta_x(ShipAttributes boat, Environment wind) {
        return boat.getVelocity()
                * sin(boat.getAngle())
                + boat.getDrift_coefficient()
                * wind.getWindVelocity()
                * sin(wind.getWindRadians());
    }

    static float delta_rotational_velocity(ShipAttributes boat, Environment wind) {
        return ((boat.getSail_center_of_effort()
                - boat.getMast_distance()
                * cos(boat.getSail_angle()))
                * force_on_sail(boat, wind)
                - boat.getRudder_distance()
                * cos(boat.getRudder_angle())
                * force_on_rudder(boat, wind)
                - boat.getAngular_friction()
                * boat.getRotational_velocity()
                * boat.getVelocity())
                / boat.getInertia();
    }

    static float delta_velocity(ShipAttributes boat, Environment wind) {
        return (sin(boat.getSail_angle())
                * force_on_sail(boat, wind)
                - sin(boat.getRudder_angle())
                * force_on_rudder(boat, wind)
                - boat.getTangential_friction()
                * boat.getVelocity()
                * boat.getVelocity())
                / boat.getMass();
    }
}
