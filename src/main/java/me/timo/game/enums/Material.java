package me.timo.game.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Material {

    ROCK(1, "textures/wall.png", true),
    TILE(2, "textures/tile.png", true),
    COBBLESTONE(3, "textures/cobblestone.png", true);

    public int id;
    public String texture;
    public boolean isSolid;

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

    Material(int id, String texture, boolean isSolid) {
        this.id = id;
        this.texture = texture;
        this.isSolid = isSolid;
    }

    public static Material get(int id) {
        Optional<Material> materials = Arrays.stream(Material.values())
                .filter(env -> env.id == id)
                .findFirst();
        return materials.get();
    }

}
