package org.statistic.eggs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.statistic.eggs.core.entity.FeedComponent;
import org.statistic.eggs.core.entity.FeedComposition;
import org.statistic.eggs.core.entity.Vitamin;

public class FoodPlanController {
    @FXML
    private Label foodPlanNameLabel;

    @FXML
    private TextArea feedDetailsTextArea;

    private FeedComposition feedComposition;

    public void setFoodComposition(FeedComposition feedComposition) {
        this.feedComposition = feedComposition;
        updateUI();
    }

    private void updateUI() {
        if (feedComposition != null) {
            foodPlanNameLabel.setText(feedComposition.getName());

            StringBuilder details = new StringBuilder();
            for (FeedComponent component : feedComposition.getComponents()) {
                details
                        .append(component.getComponentName())
                        .append(": ")
                        .append(component.getQuantity())
                        .append(System.lineSeparator());
            }

            for (Vitamin vitamins : feedComposition.getVitamins()) {
                details
                        .append(vitamins.getVitaminName())
                        .append(": ")
                        .append(vitamins.getQuantity())
                        .append(System.lineSeparator());
            }

            feedDetailsTextArea.setText(details.toString());
        }
    }

    @FXML
    private void initialize() {

    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
