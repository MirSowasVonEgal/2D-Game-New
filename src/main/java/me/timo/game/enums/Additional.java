package me.timo.game.enums;

import javafx.scene.image.Image;
import me.timo.game.manager.ImageManager;

public enum Additional {

    BREAK_1(1, "textures/additionals/BREAK_1.png"),
    BREAK_2(1, "textures/additionals/BREAK_2.png"),
    BREAK_3(1, "textures/additionals/BREAK_3.png");

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

    Additional(int id, String texture) {
        this.id = id;
        this.texture = texture;
        ImageManager.loadImage(name(), texture);
    }

}
