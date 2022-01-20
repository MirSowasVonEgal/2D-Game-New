package me.timo.game.manager;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class PlayerManager {

    public static ArrayList<String> inputs = new ArrayList<>();

    public static ArrayList<String> getInputs() {
        return inputs;
    }

    public static void setInputs(ArrayList<String> inputs) {
        PlayerManager.inputs = inputs;
    }

    public PlayerManager() {
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
