package org.statistic.eggs.dialogs;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.statistic.eggs.core.dao.StatisticDao;
import org.statistic.eggs.core.entity.FeedComponent;
import org.statistic.eggs.core.entity.FeedComposition;
import org.statistic.eggs.core.entity.Vitamin;

import java.util.ArrayList;
import java.util.List;

public class FeedCompositionDialog {

    private final List<TextField> componentNames = new ArrayList<>();
    private final List<TextField> componentQuantities = new ArrayList<>();
    private final List<TextField> vitaminNames = new ArrayList<>();
    private final List<TextField> vitaminQuantities = new ArrayList<>();

    public void showDialog(Stage parentStage) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Feed Composition");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField mixtureNameField = new TextField();
        mixtureNameField.setPromptText("Enter mixture name");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select date");

        Label componentLabel = new Label("Feed Components:");
        Button addComponentButton = new Button("Add Component");

        VBox componentBox = new VBox(5);
        addComponentButton.setOnAction(e -> addComponentField(componentBox));

        Label vitaminLabel = new Label("Vitamin Additives:");
        Button addVitaminButton = new Button("Add Vitamin");

        VBox vitaminBox = new VBox(5);
        addVitaminButton.setOnAction(e -> addVitaminField(vitaminBox));

        Button saveButton = new Button("Save");

        List<FeedComponent> components = new ArrayList<>();
        saveButton.setOnAction(e -> {
            saveMixturedComposition(mixtureNameField, datePicker);
            dialogStage.close();
        });

        vbox.getChildren().addAll(mixtureNameField, datePicker, componentLabel, addComponentButton, componentBox, vitaminLabel, addVitaminButton, vitaminBox, saveButton);

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene scene = new Scene(scrollPane, 400, 500);
        dialogStage.setScene(scene);
        dialogStage.initOwner(parentStage);
        dialogStage.showAndWait();
    }

    private void addComponentField(VBox container) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);

        TextField nameField = new TextField();
        nameField.setPromptText("Component Name");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        componentNames.add(nameField);
        componentQuantities.add(quantityField);

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Quantity:"), 0, 1);
        grid.add(quantityField, 1, 1);

        container.getChildren().add(grid);
    }

    private void addVitaminField(VBox container) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);

        TextField nameField = new TextField();
        nameField.setPromptText("Vitamin Name");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        vitaminNames.add(nameField);
        vitaminQuantities.add(quantityField);

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Quantity:"), 0, 1);
        grid.add(quantityField, 1, 1);

        container.getChildren().add(grid);
    }

    //    TODO: Implement saving to the database
    private void saveMixturedComposition(TextField mixtureNameField, DatePicker datePicker) {
        String mixtureName = mixtureNameField.getText();
        String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : "No date";

        // Create FeedComposition entity
        FeedComposition feedComposition = new FeedComposition();
        feedComposition.setName(mixtureName);
        feedComposition.setDate(date);

        // Create FeedComponent entities and add them to the FeedComposition
        List<FeedComponent> feedComponents = new ArrayList<>();
        for (int i = 0; i < componentNames.size(); i++) {
            FeedComponent component = new FeedComponent();
            component.setName(componentNames.get(i).getText());
            component.setQuantity(componentQuantities.get(i).getText());
            component.setFeedComposition(feedComposition); // Set the feed composition for this component
            feedComponents.add(component);
        }

        // Create Vitamin entities and add them to the FeedComposition
        List<Vitamin> vitamins = new ArrayList<>();
        for (int i = 0; i < vitaminNames.size(); i++) {
            Vitamin vitamin = new Vitamin();
            vitamin.setName(vitaminNames.get(i).getText());
            vitamin.setQuantity(vitaminQuantities.get(i).getText());
            vitamin.setFeedComposition(feedComposition); // Set the feed composition for this vitamin
            vitamins.add(vitamin);
        }

        feedComposition.setComponents(feedComponents);
        feedComposition.setVitamins(vitamins);

        StatisticDao.saveFeedComposition(feedComposition);
    }
}
