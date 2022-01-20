package me.timo.game.entity;

import javafx.scene.canvas.GraphicsContext;
import me.timo.game.enums.Material;

public class Block {

    public Material material;
    public Location location;

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite sprite;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
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
        sprite.setImage(material.getTexture());
        sprite.setSolid(material.isSolid());
        sprite.setName(material.name());
        this.sprite = sprite;
    }

    public void render(GraphicsContext graphicsContext) {
        getSprite().render(graphicsContext);
    }
}
