package com.uea.battle.tanks.core.physicsold;

import static com.uea.battle.tanks.core.physicsold.ShipPhysics.M_PI_4;

public class ShipAttributes {
    /* coordinates of boat */
    float m_x;
    float m_y;

    /* orientation of boat */
    float angle;
    float sail_angle;
    float rudder_angle;
    float sheet_length;

    /* is the sail free to move? */
    int sail_is_free;

    /* state variables */
    float v;
    float rotational_velocity;
    float ell;

    /* parameters */
    float drift_coefficient;
    float mass;
    float rudder_distance;
    float mast_distance;
    float rudder_lift;
    float sail_lift;
    float tangential_friction;
    float angular_friction;
    float sail_center_of_effort;
    float inertia;

    public ShipAttributes(float x, float y) {
        m_x = x;
        m_y = y;
        angle = M_PI_4;
        sail_angle = 0;
        rudder_angle = 0;
        sail_is_free = 0;
        v = 5;
        sheet_length = 1;
        rotational_velocity = 0.0F;
        inertia = 10000.0F;
        drift_coefficient = 0.05F;
        rudder_distance = 4.0F;
        angular_friction = 8000F;
        mass = 30.0F;
        tangential_friction = 0.1F;
        mast_distance = 1F;
        rudder_lift = 8000.0F;
        sail_center_of_effort = 1.0F;
        sail_lift = 1000.0F;
    }

    float getLatitude()  {
        return m_y;
    }

    float getLongitude() {
        return m_x;
    }

    void setLatitude(float latitude) {
        this.m_y = latitude;
    }

    void setLongitude(float longitude) {
        m_x = longitude;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getSail_angle() {
        return sail_angle;
    }

    public void setSail_angle(float sail_angle) {
        this.sail_angle = sail_angle;
    }

    public float getRudder_angle() {
        return rudder_angle;
    }

    public void setRudder_angle(float rudder_angle) {
        this.rudder_angle = rudder_angle;
    }

    public float getSheet_length() {
        return sheet_length;
    }

    public void setSheet_length(float sheet_length) {
        this.sheet_length = sheet_length;
    }

    public int getSail_is_free() {
        return sail_is_free;
    }

    public void setSail_is_free(int sail_is_free) {
        this.sail_is_free = sail_is_free;
    }

    public float getVelocity() {
        return v;
    }

    public void setVelocity(float v) {
        this.v = v;
    }

    public float getRotational_velocity() {
        return rotational_velocity;
    }

    public void setRotational_velocity(float rotational_velocity) {
        this.rotational_velocity = rotational_velocity;
    }

    public float getEll() {
        return ell;
    }

    public void setEll(float ell) {
        this.ell = ell;
    }

    public float getDrift_coefficient() {
        return drift_coefficient;
    }

    public void setDrift_coefficient(float drift_coefficient) {
        this.drift_coefficient = drift_coefficient;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getRudder_distance() {
        return rudder_distance;
    }

    public void setRudder_distance(float rudder_distance) {
        this.rudder_distance = rudder_distance;
    }

    public float getMast_distance() {
        return mast_distance;
    }

    public void setMast_distance(float mast_distance) {
        this.mast_distance = mast_distance;
    }

    public float getRudder_lift() {
        return rudder_lift;
    }

    public void setRudder_lift(float rudder_lift) {
        this.rudder_lift = rudder_lift;
    }

    public float getSail_lift() {
        return sail_lift;
    }

    public void setSail_lift(float sail_lift) {
        this.sail_lift = sail_lift;
    }

    public float getTangential_friction() {
        return tangential_friction;
    }

    public void setTangential_friction(float tangential_friction) {
        this.tangential_friction = tangential_friction;
    }

    public float getAngular_friction() {
        return angular_friction;
    }

    public void setAngular_friction(float angular_friction) {
        this.angular_friction = angular_friction;
    }

    public float getSail_center_of_effort() {
        return sail_center_of_effort;
    }

    public void setSail_center_of_effort(float sail_center_of_effort) {
        this.sail_center_of_effort = sail_center_of_effort;
    }

    public float getInertia() {
        return inertia;
    }

    public void setInertia(float inertia) {
        this.inertia = inertia;
    }

    @Override
    public String toString() {
        return "ShipAttributes{" +
                "m_x=" + m_x +
                ", m_y=" + m_y +
                ", angle=" + angle +
                '}';
    }
}
