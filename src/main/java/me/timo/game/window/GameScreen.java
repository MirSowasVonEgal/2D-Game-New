package me.timo.game.window;

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
        borderPane.setCenter(canvas);

        World world = new World();
        world.loadWorld("Map01");

        Player mainPlayer = new Player(Skin.DEFAULT,
                new Location((Settings.width/2.0)-0.5, (Settings.height/2.0)-0.5));
        PlayerManager playerManager = new PlayerManager(mainPlayer, world);
        playerManager.refreshInputs(mainScene);

        world.getBlocks().forEach(block -> {
            block.render(context);
        });

        world.getPlayers().forEach(player -> {
            player.render(context);
        });

        startGameLoop(playerManager, context);
        onResize(primaryStage, context, world);
        primaryStage.show();
    }

    public void startGameLoop(PlayerManager playerManager, GraphicsContext context) {
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                playerManager.handleMovement();

                context.setFill(Color.SKYBLUE);
                context.fillRect(0,0, Settings.width*64, Settings.height*64);

                playerManager.getWorld().getBlocks().forEach(block -> {
                    block.render(context);
                });

                playerManager.colliderX.render(context);
                playerManager.colliderY.render(context);

                playerManager.getWorld().getPlayers().forEach(player -> {
                    player.render(context);
                });

                playerManager.colliderX.setLocation(playerManager.getPlayer().getLocation().clone());
                playerManager.colliderY.setLocation(playerManager.getPlayer().getLocation().clone());
            }
        }.start();
    }

    public void onResize(Stage stage, GraphicsContext context, World world) {
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue() - 16;

            world.getPlayers().forEach(player -> {
                Location location = player.getLocation().clone();
                Settings.width = width / 64;
                context.getCanvas().setWidth(width);
                context.setFill(Color.SKYBLUE);
                context.fillRect(0,0, Settings.width*64, Settings.height*64);
                player.getLocation().setX(((Settings.width/2.0)-0.5)*64);
                world.getBlocks().forEach(block -> {
                    block.getLocation().add(new Vector( player.getLocation().getX() - location.getX(), 0));
                });
            });
        });
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = newValue.doubleValue() - 16;

            world.getPlayers().forEach(player -> {
                Location location = player.getLocation().clone();
                Settings.height = height / 64;
                context.getCanvas().setHeight(height);
                context.setFill(Color.SKYBLUE);
                context.fillRect(0,0, Settings.width*64, Settings.height*64);
                player.getLocation().setY(((Settings.height/2.0)-0.5)*64);
                world.getBlocks().forEach(block -> {
                    block.getLocation().add(new Vector( 0, player.getLocation().getY() - location.getY()));
                });
            });
        });
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (KeyCode.F11.equals(event.getCode())) {
                stage.setFullScreen(!stage.isFullScreen());
                stage.setMaximized(true);
                stage.setAlwaysOnTop(true);
            }
        });
    }


}
