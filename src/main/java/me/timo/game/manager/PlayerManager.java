package me.timo.game.manager;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import me.timo.game.entity.*;
import me.timo.game.enums.Skin;
import me.timo.game.utils.Settings;

import java.util.ArrayList;

public class PlayerManager {

    public ArrayList<String> inputs = new ArrayList<>();
    public Player player;
    public Sprite colliderY;
    public Sprite colliderX;
    public World world;

    public Sprite getColliderX() {
        return colliderX;
    }

    public void setColliderX(Sprite colliderX) {
        this.colliderX = colliderX;
    }

    public Sprite getColliderY() {
        return colliderY;
    }

    public void setColliderY(Sprite colliderY) {
        this.colliderY = colliderY;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<String> getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList<String> inputs) {
        this.inputs = inputs;
    }

    public PlayerManager(Player player, World world) {
        this.world = world;
        this.player = player;
        world.getPlayers().add(player);

        colliderY = new Sprite();
        colliderY.setSize(player.getSprite().getHeight()-2, 3);
        colliderY.getLocation().set(player.getLocation().getX(), player.getLocation().getY());

        colliderX = new Sprite();
        colliderX.setSize(3, player.getSprite().getHeight()-2);
        colliderX.getLocation().set(player.getLocation().getX(), player.getLocation().getY());
    }

    public void handleMovement() {
        Vector velocity = new Vector();
        if(getInputs().contains("W")) {
            velocity.setY(Settings.playerSpeed);
            colliderY.setName("TOP");
            colliderY.getLocation().set(player.getLocation().getX()+1, player.getLocation().getY()-3);
            getWorld().getSprites().add(colliderY);
        }
        if(getInputs().contains("A")) {
            velocity.setX(Settings.playerSpeed);
            colliderX.setName("LEFT");
            colliderX.getLocation().set(player.getLocation().getX()-3, player.getLocation().getY()+1);
            getWorld().getSprites().add(colliderX);
        }
        if(getInputs().contains("S")) {
            velocity.setY(-Settings.playerSpeed);
            colliderY.setName("BOTTOM");
            colliderY.getLocation().set(player.getLocation().getX()+1, player.getLocation().getY()+player.getSprite().getHeight());
            getWorld().getSprites().add(colliderY);
        }
        if(getInputs().contains("D")) {
            velocity.setX(-Settings.playerSpeed);
            colliderX.setName("RIGHT");
            colliderX.getLocation().set(player.getLocation().getX()+player.getSprite().getWidth(), player.getLocation().getY()+1);
            getWorld().getSprites().add(colliderX);
        }

        velocity.multiply(1/60.0);

        getWorld().getBlocks().forEach(block -> {
            if(block.getSprite().isSolid()) {
                if(block.getSprite().isTouching(colliderY)) {
                    double height = block.getLocation().getY();
                    if (velocity.getY() > 0 && colliderY.getName().equals("TOP")) {
                        velocity.setY(0);
                        height = ((Settings.height * 32.0) -
                                (player.getSprite().getHeight() / 2) - block.getSprite().getHeight());
                    } else if (velocity.getY() < 0 && colliderY.getName().equals("BOTTOM")) {
                        velocity.setY(0);
                        height = ((Settings.height * 32.0) -
                                (player.getSprite().getHeight() / 2) - block.getSprite().getHeight()) + 128;
                    }
                    double rate = -(block.getLocation().getY() - height);
                    block.getLocation().setY(height);
                    getWorld().getBlocks().forEach(block_ -> {
                        if (!block_.equals(block)) {
                            block_.getLocation().add(0, rate);
                        }
                    });
                }
                if(block.getSprite().isTouching(colliderX)) {
                    double width = block.getLocation().getX();
                    if (velocity.getX() > 0 && colliderX.getName().equals("LEFT")) {
                        velocity.setX(0);
                        width = ((Settings.width * 32.0) -
                                (player.getSprite().getWidth() / 2) - block.getSprite().getWidth());
                    } else if (velocity.getX() < 0 && colliderX.getName().equals("RIGHT")) {
                        velocity.setX(0);
                        width = ((Settings.width * 32.0) -
                                (player.getSprite().getWidth() / 2) - block.getSprite().getWidth() + 128);
                    }
                    double rate = -(block.getLocation().getX() - width);
                    block.getLocation().setX(width);
                    getWorld().getBlocks().forEach(block_ -> {
                        if (!block_.equals(block)) {
                            block_.getLocation().add(rate, 0);
                        }
                    });
                }
            }

        });

        getWorld().getBlocks().forEach(block -> {
            block.getLocation().add(velocity);
        });
    }

    public void refreshInputs(Scene scene) {
        scene.setOnKeyPressed((KeyEvent event) -> {
            String key = event.getCode().toString();
            if(!inputs.contains(key))
                inputs.add(key);
        });

        scene.setOnKeyReleased((KeyEvent event) -> {
            String key = event.getCode().toString();
            inputs.remove(key);
        });
    }

}
