package me.timo.game.manager;

import javafx.scene.image.Image;

import java.util.HashMap;

public class ImageManager {

    private static final HashMap<String, Image> images = new HashMap<>();

    public static void loadImage(String name, String image)  {
        images.put(name, new Image(image));
    }


    public static Image getImage(String image)  {
        if(images.containsKey(image))
            return images.get(image);
        return null;
    }
}
