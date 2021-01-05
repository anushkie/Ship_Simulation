// This class creates the wind object, creating wind and how it effects the speed and direction of a ship


package com.uea.battle.tanks.core.movement;

import java.util.ArrayList;
import java.util.Random;
import java.util.Comparator;


public class Wind {
    public WindDirection windDirection;
    public int knots;

    public Wind(WindDirection wd, int knots) {
        this.windDirection = wd;
        this.knots = knots;
    }

    public WindDirection getWindDirection() {
        return this.windDirection;
    }

    public int getKnots() {
        return this.knots;
    }

    public enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        //This is the declaration of the values of each variable of the Rank
        int value;

        //This is a constructor that highlights what the Rank is made of
        Rank(int v) {
            value = v;
        }

        //This method gets the value of the rank of the card
        public int getValue() {
            return value;
        }

        //This method get the next value
        public Rank getNext() {
            //This creates a variable that equals to the next value in the enum list
            int index = (this.ordinal() + 1) % Rank.values().length;
            //This returns the index which is the next value
            return Rank.values()[index];
        }
    }

    public enum WindDirection {
        //Need to sort the order of these
        North(1), East(2), South(3), West(4), Northwest(5), NorthEast(6),
        SouthWest(7), SouthEast(7);


        public static WindDirection getRandomDirection() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }

        int value;

        WindDirection(int v) {
            value = v;
        }

        public int getValue() {
            return value;
        }

        public WindDirection getNext() {
            int index = (this.ordinal() + 1) % WindDirection.values().length;
            return WindDirection.values()[index];
        }

        public WindDirection getBefore() {
            int index = (this.ordinal() - 1) % WindDirection.values().length;
            return WindDirection.values()[index];
        }
    }

    public static int setWindSpeed() {
        int speed;
        Random random = new Random();
        speed = random.nextInt(50) + 1;
        return speed;
    }

    public Wind newWind(Wind current) {
        //Check if there is a current wind, if so do the following
        if (current == null) {
            WindDirection a = WindDirection.getRandomDirection();
            int b = setWindSpeed();
            Wind test = new Wind(a, b);
            return test;
        } else {
            WindDirection wd1 = current.windDirection;
            int k1 = current.knots;

            ArrayList<WindDirection> windDirections = new ArrayList<>();
            windDirections.add(wd1.getBefore());
            windDirections.add(wd1.getNext());


            Random random = new Random();
            int num = random.nextInt(windDirections.size());
            WindDirection wd2 = windDirections.get(num);

            Random random1 = new Random();
            int k2 = getRandomNumber(k1 - 5, k1 + 5);

            Wind newWind = new Wind(wd2, k2);
            return newWind;
        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static void main(String[] args) {
        System.out.println(WindDirection.getRandomDirection());

        WindDirection a = WindDirection.getRandomDirection();
        int b = setWindSpeed();

        Wind test = new Wind(a, b);
        System.out.println(test.windDirection);
        System.out.println(test.knots);
    }
}
