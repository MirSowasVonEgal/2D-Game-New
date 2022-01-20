package me.timo.game.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Sprite implements Cloneable {

    public Location location = new Location();
    public Image image;
    public String type = "";
    public String name = "";
    public Color color = Color.TRANSPARENT;
    public double width = 1;
    public double height = 1;

    public boolean isSolid() {
        return isSolid;
    }

    public void setSolid(boolean solid) {
        isSolid = solid;
    }

    public boolean isSolid = false;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Sprite() {

    }

    public boolean isTouching(Sprite sprite) {
        return !(getLocation().getX() + getWidth() < sprite.getLocation().getX() ||
                sprite.getLocation().getX() + sprite.getWidth() < getLocation().getX() ||
                getLocation().getY() + getHeight() < sprite.getLocation().getY() ||
                sprite.getLocation().getY() + sprite.getHeight() < getLocation().getY());
    }

    public void setSize(double width, double height) {
        setWidth(width);
        setHeight(height);
    }

    public void setImage(String file) {
        setImage(new Image(file));
        setSize(getImage().getWidth(), getImage().getHeight());
    }

    public void render(GraphicsContext context) {
        if(getImage() != null) {
            context.drawImage(getImage(), getLocation().getX(), getLocation().getY());
        } else {
            context.setFill(getColor());
            context.fillRect(getLocation().getX(), getLocation().getY(), getWidth(), getHeight());
        }
    }

    @Override
    public Sprite clone() throws CloneNotSupportedException {
        return (Sprite) super.clone();
    }

}

