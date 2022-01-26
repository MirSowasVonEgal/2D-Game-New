package me.timo.game.window;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import me.timo.game.entity.*;
import me.timo.game.enums.Material;
import me.timo.game.enums.Skin;
import me.timo.game.manager.ImageManager;
import me.timo.game.manager.InventoryManager;
import me.timo.game.manager.PlayerManager;
import me.timo.game.manager.SaveManager;
import me.timo.game.utils.Settings;

public class GameScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle(Settings.title);

        Canvas canvas = new Canvas(Settings.width*64, Settings.height*64);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.LIGHTSKYBLUE);
        context.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        root.getChildren().add(canvas);

        Player player = new Player(Skin.DEFAULT);

        double height = canvas.getHeight();
        double width = canvas.getWidth();
        context.fillRect(0,0, width, height);
        player.setLocation(new Location(((width - player.getSprite().getWidth()) / 2),
                ((height - player.getSprite().getHeight()) / 2)));


        World world;
        SaveManager saveManager = new SaveManager();
        if(saveManager.exists("World")) {
            world = (World) saveManager.load("World");
            world.getBlocks().forEach(block -> {
                //block.getLocation().add(-64*2, -64*2);
                if(block.getData() != null && block.getData() instanceof Material) {
                    block.setMaterial(Material.ROCK);
                    block.getSprite().setAdditional(null);
                    Material material = (Material) block.getData();
                    new Thread(() -> {
                        try {
                            Thread.sleep(Double.valueOf(material.getDuration() * 1000).longValue());
                            block.setMaterial(material);
                            block.setData(null);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            });
        } else {
            world = new World();
            world.loadPreset("Map02");
            saveManager.save(world);
        }

        InventoryManager inventoryManager = new InventoryManager(root);

        PlayerManager playerManager = new PlayerManager(player, world);
        playerManager.refreshInputs(mainScene, inventoryManager);

        startGameLoop(playerManager, context, inventoryManager);
        onResize(primaryStage, context, playerManager, inventoryManager);
        onClose(primaryStage, playerManager, saveManager);

        primaryStage.getIcons().add(ImageManager.getImage("DEFAULT"));
        primaryStage.setWidth(Settings.width*64);
        primaryStage.show();
    }

    public void startGameLoop(PlayerManager playerManager, GraphicsContext context, InventoryManager inventoryManager) {

        new AnimationTimer() {
            @Override
            public void handle(long l) {
                double height = context.getCanvas().getHeight();
                double width = context.getCanvas().getWidth();

                context.setFill(Color.SKYBLUE);
                context.fillRect(0,0, context.getCanvas().getWidth(), context.getCanvas().getHeight());

                playerManager.getWorld().getBlocks().forEach(block -> {
                    block.render(context);
                });

                playerManager.getPlayer().render(context);

                playerManager.colliderX.render(context);
                playerManager.colliderY.render(context);

                playerManager.colliderX.setLocation(playerManager.getPlayer().getLocation().clone().add(1, 1));
                playerManager.colliderY.setLocation(playerManager.getPlayer().getLocation().clone().add(1, 1));

                playerManager.getWorld().getSprites().forEach(sprite -> {
                    sprite.render(context);
                    if(sprite.getName().equals("INV")) {
                        sprite.setSize(context.getCanvas().getWidth()-200, context.getCanvas().getHeight()-200);
                        sprite.setLocation(new Location(((context.getCanvas().getWidth() - sprite.getWidth()) / 2),
                                ((context.getCanvas().getHeight() - sprite.getHeight()) / 2)));
                    }
                });

                playerManager.handleMovement(context);
                inventoryManager.updateInventory();
            }
        }.start();
    }

    public void resize(double width, double height, PlayerManager playerManager, InventoryManager inventoryManager, GraphicsContext context) {
        World world = playerManager.getWorld();
        Player player = playerManager.getPlayer();
        Location location = player.getLocation().clone();
        context.getCanvas().setWidth(width);
        context.getCanvas().setHeight(height);
        context.setFill(Color.SKYBLUE);
        context.fillRect(0,0, width, height);
        player.setLocation(new Location(((width - player.getSprite().getWidth()) / 2),
                ((height - player.getSprite().getHeight()) / 2)));
        world.getBlocks().forEach(block -> {
            block.getLocation().add(new Vector( player.getLocation().getX() - location.getX(), player.getLocation().getY() - location.getY()));
        });
        inventoryManager.resizeInventory(width, height);
    }

    public void onClose(Stage stage, PlayerManager playerManager, SaveManager saveManager) {
        stage.setOnCloseRequest(event -> {
            stage.setResizable(false);
            stage.setWidth(16*64);
            stage.setHeight(12*64);
            World world = playerManager.getWorld();
            world.getSprites().removeIf(sprite -> sprite.getName().equals("COLLIDER"));
            saveManager.save(world);
            System.exit(1);
        });
    }

    public void onResize(Stage stage, GraphicsContext context, PlayerManager playerManager, InventoryManager inventoryManager) {
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            double height = context.getCanvas().getHeight();
            resize(width, height, playerManager, inventoryManager, context);

        });
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            double width = context.getCanvas().getWidth();
            double height = newValue.doubleValue();
            resize(width, height, playerManager, inventoryManager, context);
        });
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (KeyCode.F11.equals(event.getCode())) {
                stage.setFullScreen(!stage.isFullScreen());
            }
        });
    }


}
