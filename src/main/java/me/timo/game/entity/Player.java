package me.timo.game.entity;

import javafx.scene.canvas.GraphicsContext;
import me.timo.game.enums.Material;
import me.timo.game.enums.Skin;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 4437609933026331261L;

    public Skin skin;
    public Location location;

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite sprite;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        this.sprite.setLocation(location);
    }

    public Player(Skin skin) {
        setSkin(skin);

        Sprite sprite = new Sprite();
        sprite.setSkin(skin);
        setSprite(sprite);
    }

    public void render(GraphicsContext graphicsContext) {
        getSprite().render(graphicsContext);
    }
}
