package me.timo.game.enums;

public enum Material {

    WALL(1, "textures/wall.png", true);

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

}
