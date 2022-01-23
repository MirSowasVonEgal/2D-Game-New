package me.timo.game.window;

import com.google.gson.Gson;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import me.timo.game.entity.*;
import me.timo.game.enums.Skin;
import me.timo.game.manager.PlayerManager;
import me.timo.game.manager.SaveManager;
import me.timo.game.utils.Settings;

public class GameScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        Scene mainScene = new Scene(borderPane);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle(Settings.title);

        Canvas canvas = new Canvas(Settings.width*64, Settings.height*64);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.LIGHTSKYBLUE);
        context.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        borderPane.setLeft(canvas);

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
        } else {
            world = new World();
            world.loadPreset("Map01");
            saveManager.save(world);
        }

        PlayerManager playerManager = new PlayerManager(player, world);
        playerManager.refreshInputs(mainScene);

        startGameLoop(playerManager, context);
        onResize(primaryStage, context, playerManager);
        onClose(primaryStage, playerManager, saveManager);

        primaryStage.show();
    }

    public void startGameLoop(PlayerManager playerManager, GraphicsContext context) {
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                context.setFill(Color.SKYBLUE);
                context.fillRect(0,0, context.getCanvas().getWidth(), context.getCanvas().getHeight());

                playerManager.getWorld().getBlocks().forEach(block -> {
                    block.render(context);
                });


                playerManager.getWorld().getSprites().forEach(sprite -> {
                    sprite.render(context);
                });

                playerManager.getPlayer().render(context);

                playerManager.colliderX.render(context);
                playerManager.colliderY.render(context);

                playerManager.colliderX.setLocation(playerManager.getPlayer().getLocation().clone().add(1, 1));
                playerManager.colliderY.setLocation(playerManager.getPlayer().getLocation().clone().add(1, 1));

                playerManager.handleMovement(context);
            }
        }.start();
    }

    public void onClose(Stage stage, PlayerManager playerManager, SaveManager saveManager) {
        stage.setOnCloseRequest(event -> {
            stage.setResizable(false);
            stage.setWidth(16*64);
            stage.setHeight(12*64);
            World world = playerManager.getWorld();
            world.getSprites().removeIf(sprite -> sprite.getName().equals("COLLIDER"));
            saveManager.save(world);
        });
    }

    public void onResize(Stage stage, GraphicsContext context, PlayerManager playerManager) {
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            double height = context.getCanvas().getHeight();

            World world = playerManager.getWorld();
            Player player = playerManager.getPlayer();
            Location location = player.getLocation().clone();
            context.getCanvas().setWidth(width);
            context.setFill(Color.SKYBLUE);
            context.fillRect(0,0, width, height);
            player.setLocation(new Location(((width - player.getSprite().getWidth()) / 2),
                    ((height - player.getSprite().getHeight()) / 2)));
            world.getBlocks().forEach(block -> {
                block.getLocation().add(new Vector( player.getLocation().getX() - location.getX(), 0));
            });
        });
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = newValue.doubleValue();
            double width = context.getCanvas().getWidth();

            World world = playerManager.getWorld();
            Player player = playerManager.getPlayer();
            Location location = player.getLocation().clone();
            context.getCanvas().setHeight(height);
            context.setFill(Color.SKYBLUE);
            context.fillRect(0,0, width, height);
            player.setLocation(new Location(((width - player.getSprite().getWidth()) / 2),
                    ((height - player.getSprite().getHeight()) / 2)));
            world.getBlocks().forEach(block -> {
                block.getLocation().add(new Vector(0, player.getLocation().getY() - location.getY()));
            });
        });
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (KeyCode.F11.equals(event.getCode())) {
                stage.setFullScreen(!stage.isFullScreen());
            }
        });
    }


}
