package me.timo.game.manager;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import me.timo.game.entity.Item;
import me.timo.game.enums.Material;

import java.util.ArrayList;

public class InventoryManager {

    private Pane root;
    private ScrollPane scrollPane;
    private GridPane gridPane;
    private boolean isScrollbar = false;
    private boolean opened = false;

    private ArrayList<Item> items = new ArrayList<>();

    public InventoryManager(Pane root) {
        this.root = root;

        gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));

        scrollPane = new ScrollPane();
        scrollPane.setLayoutX(128);
        scrollPane.setLayoutY(128);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #C0C0C0;");
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(gridPane);
        scrollPane.setVisible(false);

        for (int i = 0; i < 100; i++) {
            Item item = new Item("Kohle", Material.COAL_ITEM);
            items.add(item);
        }
        for (int i = 0; i < 10; i++) {
            items.add(new Item());
        }


        updateInventory();

        root.getChildren().add(scrollPane);
    }

    public void updateInventory() {
        gridPane.getChildren().clear();
        int column = 0;
        int row = 0;
        for (Item item : items) {
            if(item.getMaterial() == null) {
                Rectangle rectangle = new Rectangle(item.getSize(), item.getSize());
                rectangle.setFill(Color.TRANSPARENT);
                gridPane.add(rectangle, column, row);
            } else {
                ImageView imageView = new ImageView(item.getMaterial().getImage());
                imageView.setFitHeight(item.getSize());
                imageView.setFitWidth(item.getSize());
                Text text = new Text();
                text.setText(item.getAmount() + " " + item.getName());
                text.setTextAlignment(TextAlignment.CENTER);
                gridPane.add(imageView, column, row);
                gridPane.add(text, column, row, 1, 2);
            }
            if(column > 8) {
                column = 0;
                row++;
            } else {
                column++;
            }
        }
    }

    public void resizeInventory(double width, double height) {
        scrollPane.setPrefSize(width-256, height-256);
        isScrollbar = gridPane.getHeight() > height-256;
        int scrollbar = 0;
        if (isScrollbar)
            scrollbar = 10;
        double size = (width - 256 - 115 - scrollbar) / 10;
        items.forEach(item -> item.setSize(size));
    }

    public void openInventory() {
        scrollPane.setVisible(!opened);
        opened = !opened;
    }

}
