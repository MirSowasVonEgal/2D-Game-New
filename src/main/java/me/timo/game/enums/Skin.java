package me.timo.game.enums;

public enum Skin {

    DEFAULT(1, "textures/player.png", true);

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

    Skin(int id, String texture, boolean isSolid) {
        this.id = id;
        this.texture = texture;
        this.isSolid = isSolid;
    }

}
