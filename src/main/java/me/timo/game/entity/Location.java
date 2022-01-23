package me.timo.game.entity;

import java.io.Serializable;

public class Location implements Cloneable, Serializable {
    private static final long serialVersionUID = 4437609933026331261L;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double y;
    public double x;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Location() {
        this.x = 0;
        this.y = 0;
    }

    public Location set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Location add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Location add(Vector vector) {
        this.x += vector.getX();
        this.y += vector.getY();
        return this;
    }

    public Location remove(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Location multiply(double m) {
        this.x *= m;
        this.y *= m;
        return this;
    }

    public Location get() {
        return this;
    }

    @Override
    public Location clone() {
        try {
            return (Location) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString() {
        return "X: " + x + " | Y: " + y;
    }
}
