package me.timo.game.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import me.timo.game.enums.Additional;
import me.timo.game.enums.Material;
import me.timo.game.enums.Skin;

import java.io.Serializable;
import java.util.Objects;

public class Sprite implements Cloneable, Serializable {
    private static final long serialVersionUID = 4437609933026331261L;

    public Location location = new Location();
    public Material material;
    public Additional additional;
    public Skin skin;
    public String type = "";
    public String name = "";
    public String data = "";
    public String color = Color.TRANSPARENT.toString();
    public double width;
    public double height;

    public Additional getAdditional() {
        return additional;
    }

    public void setAdditional(Additional additional) {
        this.additional = additional;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Material getMaterial() {
        return material;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setMaterial(Material material) {
        if(material.getImage() != null) {
            setSize(material.getImage().getWidth(), material.getImage().getHeight());
        }
        setName(material.name());
        setSolid(material.isSolid());
        this.material = material;
    }

    public void setSkin(Skin skin) {
        if(skin.getImage() != null) {
            setSize(skin.getImage().getWidth(), skin.getImage().getHeight());
        }
        setName(skin.name());
        setSolid(true);
        this.skin = skin;
    }

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
        return Color.web(color);
    }

    public void setColor(Color color) {
        this.color = color.toString();
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

    public void render(GraphicsContext context) {
        if(material == null && skin == null) {
            context.setFill(getColor());
            context.fillRect(getLocation().getX(), getLocation().getY(), getWidth(), getHeight());
        } else if(material != null) {
            if(width == 0)
                width = material.getImage().getWidth();
            if(height == 0)
                height = material.getImage().getHeight();
            context.drawImage(material.getImage(), getLocation().getX(), getLocation().getY(), getWidth(), getHeight());
        } else {
            if(width == 0)
                width = skin.getImage().getWidth();
            if(height == 0)
                height = skin.getImage().getHeight();
            context.drawImage(skin.getImage(), getLocation().getX(), getLocation().getY(), getWidth(), getHeight());
        }
        if(additional != null) {
            if (width == 0)
                width = additional.getImage().getWidth();
            if (height == 0)
                height = additional.getImage().getHeight();
            context.drawImage(additional.getImage(), getLocation().getX(), getLocation().getY(), getWidth(), getHeight());
        }
    }

    @Override
    public Sprite clone() throws CloneNotSupportedException {
        return (Sprite) super.clone();
    }

}

