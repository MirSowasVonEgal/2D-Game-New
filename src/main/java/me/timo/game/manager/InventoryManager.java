package me.timo.game.manager;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import me.timo.game.entity.Item;
import me.timo.game.enums.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class InventoryManager {

    private Pane root;
    private ScrollPane scrollPane;
    private GridPane gridPane;
    private boolean isScrollbar = false;
    private boolean opened = false;
    private double itemSize;

    public double getItemSize() {
        return itemSize;
    }

    public void setItemSize(double itemSize) {
        this.itemSize = itemSize;
    }

    private ArrayList<Item> items = new ArrayList<>();

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public boolean isScrollbar() {
        return isScrollbar;
    }

    public void setScrollbar(boolean scrollbar) {
        isScrollbar = scrollbar;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public InventoryManager(Pane root) {
        this.root = root;

        gridPane = new GridPane();
        gridPane.setVgap(70);
        gridPane.setHgap(70);
        gridPane.setStyle("-fx-background-image: url('textures/guis/INVENTORY_GUI.png');" +
                "-fx-background-repeat: stretch;" +
                "-fx-background-size: cover;"+
                "-fx-background-size: 100% 100%;");

        scrollPane = new ScrollPane();
        scrollPane.setLayoutX(128);
        scrollPane.setLayoutY(128);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setBackground(null);
        scrollPane.setContent(gridPane);
        scrollPane.setVisible(false);

        for (int i = 0; i < 8; i++) {
            items.add(new Item("DIAMOND", Material.DIAMOND_ITEM));
        }

        updateInventory();

        root.getChildren().add(scrollPane);
    }

    public void addItem(Item item, int amount) {
        Optional<Item> optionalItem = getItems().stream()
                .filter(current -> current.getMaterial().name().equals(item.getMaterial().name())).findFirst();
        optionalItem.ifPresent(value -> value.addAmount(amount));
        if(!optionalItem.isPresent()) {
            getItems().add(item);
        }
        updateInventory();
    }

    public void updateInventory() {
        gridPane.getChildren().clear();
        int column = 0;
        int row = 0;
        items.sort(Comparator.comparing(a -> a.getMaterial().getPriority()));
        for (Item item : items) {
            if(item.getMaterial() == null) {
                Rectangle rectangle = new Rectangle(itemSize, itemSize);
                rectangle.setFill(Color.TRANSPARENT);
                gridPane.add(rectangle, column, row);
            } else {
                ImageView imageView = new ImageView(item.getMaterial().getImage());
                imageView.setFitHeight(itemSize);
                imageView.setFitWidth(itemSize);
                gridPane.add(imageView, column, row);
                Text text = new Text();
                text.setFont(new Font(itemSize / 4));
                text.setFont(Font.font("Futura", FontWeight.BOLD, itemSize / 4));
                text.setText(item.getAmount() + " " + item.getName());
                text.setTextAlignment(TextAlignment.CENTER);
                gridPane.add(text, column, row);
                GridPane.setMargin(text, new Insets(-itemSize / 5));
                GridPane.setValignment(text, VPos.BOTTOM);
                GridPane.setHalignment(text, HPos.CENTER);
            }
            if(column > 6) {
                column = 0;
                row++;
            } else {
                column++;
            }
        }
    }

    public void resizeInventory(double width, double height) {
        gridPane.setPadding(new Insets(height / 8 - 50, width / 25, height / 4.0 - 180, width / 25));
        scrollPane.setPrefSize(width-256, height-256);
        isScrollbar = gridPane.getHeight() > height-256;
        int scrollbar = 0;
        if (isScrollbar)
            scrollbar = 10;
        itemSize = (width - 256 - (70*9) - ((width / 25) * 2) - scrollbar) / 8;
        System.out.println(itemSize);
    }

    public void openInventory() {
        scrollPane.setVisible(!opened);
        opened = !opened;
    }

}
