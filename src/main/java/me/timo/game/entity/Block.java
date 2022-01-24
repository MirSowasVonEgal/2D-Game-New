package me.timo.game.entity;

import javafx.scene.canvas.GraphicsContext;
import me.timo.game.enums.Material;

import java.io.Serializable;

public class Block implements Serializable {
    private static final long serialVersionUID = 4437609933026331261L;

    public Material material;
    public Location location;
    public int brokenState = -1;
    public Sprite sprite;
    public Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getBrokenState() {
        return brokenState;
    }

    public void setBrokenState(int brokenState) {
        this.brokenState = brokenState;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
        getSprite().setMaterial(material);
        setBrokenState(material.getDefaultBrokenState());
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Block(Material material) {
        this.material = material;
    }

    public Block(Material material, Location location) {
        this.material = material;
        this.location = location;

        Sprite sprite = new Sprite();
        sprite.setLocation(location.multiply(64));
        sprite.setMaterial(material);
        if(material.getDefaultBrokenState() != 0)
            brokenState = material.getDefaultBrokenState();
        this.sprite = sprite;
    }

    public void render(GraphicsContext graphicsContext) {
        getSprite().render(graphicsContext);
    }
}
