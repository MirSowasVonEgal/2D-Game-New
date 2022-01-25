package me.timo.game.entity;

import me.timo.game.enums.Material;

import java.util.ArrayList;

public class Inventory {

    public ArrayList<Material> items = new ArrayList<>();

    public ArrayList<Material> getItems() {
        return items;
    }

    public void setItems(ArrayList<Material> items) {
        this.items = items;
    }

    public Inventory() {

    }

}
