package com.uea.battle.tanks.core.ship;

public class Vector {
    double a, l;
    public double x, y;

    float getAngleDegree() {
        return (float) (180 * a / Math.PI);
    }

    public double getAngle() {
        return a;
    }

    public void setAngle(double value) {
        a = value;
        if (a < 0) a += 2 * Math.PI;
        else if (a > 2 * Math.PI) a -= 2 * Math.PI;
        x = Math.cos(a) * l;
        y = Math.sin(a) * l;
    }

    public double getLength() {
        return l;
    }

    public void setLength(double value) {
        x *= value / l;
        y *= value / l;
        l = value;
    }

    public Vector(double X, double Y) {
        x = X;
        y = Y;
        a = 0;
        l = 1;
    }

    public void calcLength() {
        l = Math.sqrt(x * x + y * y);
    }

    public void calcAngle() {
        a = Math.atan2(y, x);
    }

    public static double scalarMult(Vector a, Vector b) {
        return a.x * b.x + a.y * b.y;
    }

    public static int signCos(Vector a, Vector b) {
        return scalarMult(a, b) > 0 ? 1 : -1;
    }

    public Vector projection(Vector to) {
        Vector r;
        r = multiply(scalarMult(this, to) * to.getLength() * to.getLength(), to);
        return r;
    }

    public static Vector multiply(double a, Vector v) {
        return new Vector(a * v.x, a * v.y);
    }

    public static Vector plus(Vector a, Vector b) {
        return new Vector(a.x + b.x, a.y + b.y);
    }

    public static Vector minus(Vector a, Vector b) {
        return new Vector(a.x - b.x, a.y - b.y);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "a=" + a +
                ", l=" + l +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
