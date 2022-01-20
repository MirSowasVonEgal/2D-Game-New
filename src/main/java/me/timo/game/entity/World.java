package me.timo.game.entity;

import me.timo.game.enums.Material;
import me.timo.game.utils.Settings;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class World {

    public ArrayList<Block> blocks = new ArrayList<>();
    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<Sprite> sprites = new ArrayList<>();

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public void setSprites(ArrayList<Sprite> sprites) {
        this.sprites = sprites;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public World() {

    }

    public void createWorld() {
        for (int i = 0; i < 16; i++) {
            blocks.add(new Block(Material.ROCK, new Location(i, 0)));
            blocks.add(new Block(Material.ROCK, new Location(i, 11)));
        }
    }

    public void loadWorld(String name) {
        try (BufferedReader br = new BufferedReader(new FileReader(Objects.requireNonNull(getClass().
                getClassLoader().getResource("maps/"+name)).getFile()))) {
            String line;
            int y = 0;
            Location playerLocation = new Location();
            while ((line = br.readLine()) != null) {
                String newLine = line.replace(" ", "");
                for (int i = 0; i < newLine.length(); i++) {
                    char ObjectId = newLine.charAt(i);
                    if(ObjectId == 'P') {
                        playerLocation.set(i, y);
                    } else {
                        int blockId = Character.getNumericValue(ObjectId);
                        if (blockId != 0)
                            blocks.add(new Block(Material.get(blockId), new Location(i, y)));
                    }
                }
                y++;
            }
            getBlocks().forEach(block -> {
                block.getLocation().add((-playerLocation.getX() + (Settings.width / 2.0) - 0.5 ) *64,
                        (-playerLocation.getY() + (Settings.height / 2.0) - 0.5 )*64);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
