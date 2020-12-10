package com.uea.battle.tanks.core.movement;

public class Position {

    private float x;

    private float y;

    private float angle;

    /**
     * Creates a new position with given parameters.
     * @param x The x to set
     * @param y The y to set
     * @param angle The angle to set
     */
    public Position(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    /**
     * Copy constructor
     * @param position The position to set
     */
    public Position(Position position) {
        this(position.getX(), position.getY(), position.getAngle());
    }


    /**
     * Adds offset to current angle and return the position
     * @param angle The angle to add
     * @return The position object
     */
    public Position addAngle(float angle) {
        this.setAngle(this.getAngle() + angle);
        return this;
    }

    /**
     * Adds offest to the current x and returns the positon
     * @param x The x to add
     * @return The position object
     */
    public Position addX(float x) {
        this.setX(getX() + x);
        return this;
    }

    /**
     * Adds offset to the current y and returns the position
     * @param y The y to add
     * @return The position object
     */
    public Position addY(float y) {
        this.setY(getY() + y);
        return this;
    }


    /**
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * @return the angle
     */
    public float getAngle() {
        return angle;
    }

    /**
     * @param angle the angle to set
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }

}
