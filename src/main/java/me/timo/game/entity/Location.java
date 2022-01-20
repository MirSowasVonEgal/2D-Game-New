package me.timo.game.entity;

public class Location {

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

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location() {
        this.x = 0;
        this.y = 0;
    }

    public Location set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Location add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Location remove(int x, int y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Location multiply(int m) {
        this.x *= m;
        this.y *= m;
        return this;
    }

    public Location get() {
        return this;
    }
}
