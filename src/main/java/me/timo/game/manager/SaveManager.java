package me.timo.game.manager;

import com.google.gson.Gson;

import java.io.*;

public class SaveManager {

    public SaveManager() {

    }

    public void save(Object object) {
        try {
            FileOutputStream out = new FileOutputStream(object.getClass().getSimpleName() + ".dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(out);
            objectOut.writeObject(object);
            objectOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object load(String file) {
        try {
            if(!new File(file + ".dat").exists()) return null;
            ObjectInputStream in = new ObjectInputStream( new FileInputStream(file + ".dat"));
            return in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean exists(String file) {
        return new File(file + ".dat").exists();
    }


}
