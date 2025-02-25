package org.statistic.eggs;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FeedCompositionDialog {

    private List<TextField> componentNames = new ArrayList<>();
    private List<TextField> componentQuantities = new ArrayList<>();
    private List<TextField> vitaminNames = new ArrayList<>();
    private List<TextField> vitaminQuantities = new ArrayList<>();

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
        saveButton.setOnAction(e -> {
            saveData(mixtureNameField, datePicker);
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
    private void saveData(TextField mixtureNameField, DatePicker datePicker) {
        String mixtureName = mixtureNameField.getText();
        String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : "No date";

        System.out.println("Mixture Name: " + mixtureName);
        System.out.println("Date: " + date);
        System.out.println("Feed Components:");

        for (int i = 0; i < componentNames.size(); i++) {
            System.out.println("- " + componentNames.get(i).getText() + ": " + componentQuantities.get(i).getText());
        }

        System.out.println("Vitamin Additives:");
        for (int i = 0; i < vitaminNames.size(); i++) {
            System.out.println("- " + vitaminNames.get(i).getText() + ": " + vitaminQuantities.get(i).getText());
        }
    }
}
