package me.timo.game.manager;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import me.timo.game.entity.*;
import me.timo.game.enums.Additional;
import me.timo.game.enums.Material;
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

        colliderY = new Sprite();
        colliderY.setName("COLLIDER");
        colliderY.setSize(player.getSprite().getWidth()-2, 3);
        colliderY.getLocation().set(player.getLocation().getX(), player.getLocation().getY());

        colliderX = new Sprite();
        colliderX.setName("COLLIDER");
        colliderX.setSize(3, player.getSprite().getHeight()-2);
        colliderX.getLocation().set(player.getLocation().getX(), player.getLocation().getY());
    }

    public void handleMovement(GraphicsContext context) {
        Vector velocity = new Vector();
        if(getInputs().contains("W")) {
            velocity.addY(Settings.playerSpeed);
            colliderY.setData("TOP");
            colliderY.getLocation().set(player.getLocation().getX()+1, player.getLocation().getY()-3);
            getWorld().getSprites().add(colliderY);
        }
        if(getInputs().contains("A")) {
            velocity.addX(Settings.playerSpeed);
            colliderX.setData("LEFT");
            colliderX.getLocation().set(player.getLocation().getX()-3, player.getLocation().getY()+1);
            getWorld().getSprites().add(colliderX);
        }
        if(getInputs().contains("S")) {
            velocity.addY(-Settings.playerSpeed);
            colliderY.setData("BOTTOM");
            colliderY.getLocation().set(player.getLocation().getX()+1, player.getLocation().getY()+player.getSprite().getHeight());
            getWorld().getSprites().add(colliderY);
        }
        if(getInputs().contains("D")) {
            velocity.addX(-Settings.playerSpeed);
            colliderX.setData("RIGHT");
            colliderX.getLocation().set(player.getLocation().getX()+player.getSprite().getWidth(), player.getLocation().getY()+1);
            getWorld().getSprites().add(colliderX);
        }

        velocity.multiply(1/60.0);

        double canvasHeight = context.getCanvas().getHeight();
        double canvasWidth = context.getCanvas().getWidth();

        getWorld().getBlocks().forEach(block -> {
            if(block.getSprite().isSolid()) {
                if(block.getSprite().isTouching(colliderY)) {
                    double height = block.getLocation().getY();
                    if (velocity.getY() > 0 && colliderY.getData().equals("TOP")) {
                        velocity.setY(0);
                        height = ((canvasHeight - getPlayer().getSprite().getHeight()) / 2) - block.getSprite().getHeight();
                    } else if (velocity.getY() < 0 && colliderY.getData().equals("BOTTOM")) {
                        velocity.setY(0);
                        height = ((canvasHeight + getPlayer().getSprite().getHeight()) / 2);
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
                    if (velocity.getX() > 0 && colliderX.getData().equals("LEFT")) {
                        velocity.setX(0);
                        width = ((canvasWidth - getPlayer().getSprite().getWidth()) / 2) - block.getSprite().getWidth();
                    } else if (velocity.getX() < 0 && colliderX.getData().equals("RIGHT")) {
                        velocity.setX(0);
                        width = ((canvasWidth + getPlayer().getSprite().getWidth()) / 2);
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

    public void handleInteractions(String key, InventoryManager inventoryManager) {
        if(key.equals("SPACE")) {
            Block block = getWorld().getBlocks().stream().filter(current ->
                            (current.getSprite().isTouching(colliderY) || current.getSprite().isTouching(colliderX))
                            && current.getBrokenState() != -1).findFirst().orElse(null);
            if(block == null)
                return;

            double state = Double.parseDouble(block.getBrokenState() +"") / block.getMaterial().getDefaultBrokenState();

            if(state > 2/3.0) {
                block.getSprite().setAdditional(Additional.BREAK_1);
            } else if(state > 1/3.0) {
                block.getSprite().setAdditional(Additional.BREAK_2);
            } else if(state > 0) {
                block.getSprite().setAdditional(Additional.BREAK_3);
            } else {
                block.getSprite().setAdditional(null);
                Material material = block.getMaterial();
                if(block.getMaterial().toString().equals("STONE")) {
                    block.setMaterial(Material.ROCK);
                } else {
                    block.setMaterial(Material.STONE);
                }
                inventoryManager.addItem(new Item(material.name(), Material.valueOf(material.name() + "_ITEM")), 1);
                if(block.getData() == null)
                    block.setData(material);
                new Thread(() -> {
                    try {
                        if(block.getData() != null && block.getData() instanceof Material) {
                            Material data = (Material) block.getData();
                            Thread.sleep(Double.valueOf(data.getDuration() * 1000).longValue());
                            block.getSprite().setAdditional(null);
                            block.setMaterial(data);
                            block.setData(null);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                return;
            }
            block.setBrokenState(block.getBrokenState()-1);
        } else if(key.equals("ALT")) {
            inventoryManager.openInventory();
        }
    }

    public void refreshInputs(Scene scene, InventoryManager inventoryManager) {
        scene.setOnKeyPressed((KeyEvent event) -> {
            String key = event.getCode().toString();
            if(!inputs.contains(key))
                inputs.add(key);
        });

        scene.setOnKeyReleased((KeyEvent event) -> {
            String key = event.getCode().toString();
            inputs.remove(key);
            handleInteractions(key, inventoryManager);
        });
    }

}
