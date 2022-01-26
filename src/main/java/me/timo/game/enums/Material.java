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
    COAL(5, "textures/materials/ores/COAL_ORE.png", true, 6, 10),
    IRON(6, "textures/materials/ores/IRON_ORE.png", true, 9, 20),
    GOLD(7, "textures/materials/ores/GOLD_ORE.png", true, 12, 30),
    DIAMOND(8, "textures/materials/ores/DIAMOND_ORE.png", true, 15, 1),
    STONE_ITEM(104, "textures/items/ores/STONE.png", false, "F99"),
    COAL_ITEM(105, "textures/items/ores/COAL.png", false, "F98"),
    IRON_ITEM(105, "textures/items/ores/IRON.png", false, "F97"),
    GOLD_ITEM(106, "textures/items/ores/GOLD.png", false, "F96"),
    DIAMOND_ITEM(107, "textures/items/ores/DIAMOND.png", false, "F95");

    public int id;
    public String texture;
    public boolean isSolid;
    public int defaultBrokenState;
    public double duration;
    public String priority = "Z99";

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

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

    Material(int id, String texture, boolean isSolid, String priority) {
        this.id = id;
        this.texture = texture;
        this.isSolid = isSolid;
        this.defaultBrokenState = -1;
        this.priority = priority;
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
