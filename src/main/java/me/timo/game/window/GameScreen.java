package me.timo.game.window;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import me.timo.game.entity.*;
import me.timo.game.enums.Material;
import me.timo.game.enums.Skin;
import me.timo.game.manager.PlayerManager;
import me.timo.game.utils.Settings;

import java.util.ArrayList;

public class GameScreen extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Scene mainScene = new Scene(borderPane);
        primaryStage.setScene(mainScene);

        Canvas canvas = new Canvas(Settings.width*64, Settings.height*64);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.LIGHTSKYBLUE);
        context.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        borderPane.setCenter(canvas);

        World world = new World();
        world.createWorld();

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

                playerManager.getWorld().getPlayers().forEach(player -> {
                    player.render(context);
                });
            }
        }.start();
    }

}
