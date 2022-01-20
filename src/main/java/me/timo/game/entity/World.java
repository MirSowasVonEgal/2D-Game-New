package me.timo.game.entity;

import me.timo.game.enums.Material;
import me.timo.game.enums.Skin;

import java.util.ArrayList;

public class World {

    public ArrayList<Block> blocks = new ArrayList<>();
    public ArrayList<Player> players = new ArrayList<>();

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
        blocks.add(new Block(Material.WALL, new Location(0, 0)));
        blocks.add(new Block(Material.WALL, new Location(1, 0)));
    }

}
