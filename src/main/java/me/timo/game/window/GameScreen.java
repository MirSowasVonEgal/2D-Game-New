package me.timo.game.window;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import me.timo.game.entity.Location;
import me.timo.game.entity.Sprite;
import me.timo.game.entity.World;
import me.timo.game.enums.Material;
import me.timo.game.manager.PlayerManager;

public class GameScreen extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Scene mainScene = new Scene(borderPane);
        primaryStage.setScene(mainScene);

        Canvas canvas = new Canvas(16*64, 12*64);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.LIGHTSKYBLUE);
        context.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        borderPane.setCenter(canvas);

        World world = new World();
        world.createWorld();

        PlayerManager playerManager = new PlayerManager();
        playerManager.refreshInputs(mainScene);

        world.getBlocks().forEach(block -> {
            block.render(context);
        });

        world.getPlayers().forEach(player -> {
            player.render(context);
        });

        primaryStage.show();
    }

}
