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
    STONE(4, "textures/materials/ores/STONE.png", true, 3, 5),
    COAL(5, "textures/materials/ores/COAL_ORE.png", true, 6, 15),
    STONE_ITEM(104, "textures/items/ores/STONE.png", false),
    COAL_ITEM(105, "textures/items/ores/COAL_ORE.png", false);

    public int id;
    public String texture;
    public boolean isSolid;
    public int defaultBrokenState;
    public double duration;

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

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
        this.defaultBrokenState = -1;
        ImageManager.loadImage(name(), texture);
    }

    Material(int id, String texture, boolean isSolid, int defaultBrokenState, double duration) {
        this.id = id;
        this.texture = texture;
        this.isSolid = isSolid;
        this.defaultBrokenState = defaultBrokenState;
        this.duration = duration;
        ImageManager.loadImage(name(), texture);
    }

    public static Material get(int id) {
        Optional<Material> materials = Arrays.stream(Material.values())
                .filter(env -> env.id == id)
                .findFirst();
        return materials.get();
    }

}
