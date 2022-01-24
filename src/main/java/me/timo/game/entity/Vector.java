package me.timo.game.entity;

import java.io.Serializable;

public class Vector implements Serializable {
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

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public Vector set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector addY(double y) {
        this.y += y;
        return this;
    }

    public Vector addX(double x) {
        this.x += x;
        return this;
    }

    public Vector remove(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector multiply(double m) {
        this.x *= m;
        this.y *= m;
        return this;
    }

    public Vector get() {
        return this;
    }

    public String toString() {
        return "X: " + x + " | Y: " + y;
    }
}
