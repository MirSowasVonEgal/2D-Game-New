package me.timo.game.entity;

import javafx.scene.canvas.GraphicsContext;
import me.timo.game.enums.Material;
import me.timo.game.enums.Skin;

public class Player {

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
    }

    public Player(Skin skin, Location location) {
        setSkin(skin);
        setLocation(location);

        Sprite sprite = new Sprite();
        sprite.setLocation(location.multiply(64));
        sprite.setImage(skin.getTexture());
        sprite.setSolid(skin.isSolid());
        sprite.setName(skin.name());
        setSprite(sprite);
    }

    public void render(GraphicsContext graphicsContext) {
        getSprite().render(graphicsContext);
    }
}
