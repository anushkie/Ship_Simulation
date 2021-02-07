package com.uea.battle.tanks.core.ship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorTest {

    @Test
    void testGetAngleDegree() {
        Vector vector = new Vector(0, 0);
        vector.a = Math.PI / 2;

        assertEquals(90, vector.getAngleDegree());
    }

    @Test
    void testGetLength() {
        Vector vector = new Vector(4, 3);

        vector.calcLength();

        assertEquals(5.0, vector.getLength());
    }

    @Test
    void testScalarMultiply() {
        Vector a = new Vector(2, 3);
        Vector b = new Vector(4, 5);

        double product = Vector.scalarMult(a, b);

        assertEquals(23, product);
    }
}