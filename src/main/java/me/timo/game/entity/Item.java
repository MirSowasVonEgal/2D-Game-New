package me.timo.game.entity;

import me.timo.game.enums.Material;

public class Item {

    public int amount = 1;
    public int durability = -1;
    public Material material;
    public String name = "";
    public double size;

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item(String name, Material material) {
        this.name = name;
        this.material = material;
    }

    public Item() {

    }

}
