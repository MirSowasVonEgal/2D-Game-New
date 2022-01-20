package me.timo.game.entity;

public class Vector {

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int y;
    public int x;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public Vector set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector remove(int x, int y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector multiply(int m) {
        this.x *= m;
        this.y *= m;
        return this;
    }

    public Vector get() {
        return this;
    }
}
