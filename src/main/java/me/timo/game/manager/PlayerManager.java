package me.timo.game.manager;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import me.timo.game.entity.Location;
import me.timo.game.entity.Player;
import me.timo.game.entity.Vector;
import me.timo.game.entity.World;
import me.timo.game.enums.Skin;
import me.timo.game.utils.Settings;

import java.util.ArrayList;

public class PlayerManager {

    public ArrayList<String> inputs = new ArrayList<>();
    public Player player;
    public World world;

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
    }

    public void handleMovement() {
        Vector velocity = new Vector();
        if(getInputs().contains("W")) {
            velocity.setY(Settings.playerSpeed);
        }
        if(getInputs().contains("A")) {
            velocity.setX(Settings.playerSpeed);
        }
        if(getInputs().contains("S")) {
            velocity.setY(-Settings.playerSpeed);
        }
        if(getInputs().contains("D")) {
            velocity.setX(-Settings.playerSpeed);
        }

        velocity.multiply(1/60.0);

        getWorld().getBlocks().forEach(block -> {

            if(block.getSprite().isSolid()
                    && getPlayer().getSprite().isTouching(block.getSprite())) {
                if(velocity.getY() > 0) {
                    velocity.setY(0);
                    block.getLocation().setY(block.getSprite().getHeight());
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
