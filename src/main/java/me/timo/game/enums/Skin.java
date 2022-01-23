package me.timo.game.enums;

import javafx.scene.image.Image;
import me.timo.game.manager.ImageManager;

import java.io.Serializable;

public enum Skin implements Serializable {

    DEFAULT(1, "textures/player.png");

    public int id;
    public String texture;

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

    public Image getImage() {
        if(ImageManager.getImage(name()) == null)
            return null;
        return ImageManager.getImage(name());
    }

    Skin(int id, String texture) {
        this.id = id;
        this.texture = texture;
        ImageManager.loadImage(name(), texture);
    }

}
