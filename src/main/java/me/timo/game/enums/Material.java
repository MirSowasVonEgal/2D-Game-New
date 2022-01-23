package me.timo.game.enums;

import javafx.scene.image.Image;
import me.timo.game.manager.ImageManager;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

public enum Material implements Serializable {

    ROCK(1, "textures/wall.png", true),
    TILE(2, "textures/tile.png", true),
    COBBLESTONE(3, "textures/cobblestone.png", true),
    COAL(4, "textures/cobblestone.png", true, 5);

    public int id;
    public String texture;
    public boolean isSolid;
    public int defaultBrokenState;

    public int getDefaultBrokenState() {
        return defaultBrokenState;
    }

    public void setDefaultBrokenState(int defaultBrokenState) {
        this.defaultBrokenState = defaultBrokenState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public void setSolid(boolean solid) {
        isSolid = solid;
    }

    public Image getImage() {
        if(ImageManager.getImage(name()) == null)
            return null;
        return ImageManager.getImage(name());
    }

    Material(int id, String texture, boolean isSolid) {
        this.id = id;
        this.texture = texture;
        this.isSolid = isSolid;
        ImageManager.loadImage(name(), texture);
    }

    Material(int id, String texture, boolean isSolid, int defaultBrokenState) {
        this.id = id;
        this.texture = texture;
        this.isSolid = isSolid;
        this.defaultBrokenState = defaultBrokenState;
        ImageManager.loadImage(name(), texture);
    }

    public static Material get(int id) {
        Optional<Material> materials = Arrays.stream(Material.values())
                .filter(env -> env.id == id)
                .findFirst();
        return materials.get();
    }

}
