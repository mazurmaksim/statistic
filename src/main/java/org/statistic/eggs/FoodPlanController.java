package org.statistic.eggs;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.statistic.eggs.core.dao.StatisticDao;
import org.statistic.eggs.core.entity.Counter;
import org.statistic.eggs.core.entity.FeedComponent;
import org.statistic.eggs.core.entity.FeedComposition;
import org.statistic.eggs.core.entity.Vitamin;
import org.statistic.eggs.core.persistence.Persistence;
import org.statistic.eggs.core.views.DaysView;
import org.statistic.eggs.core.views.StatisticView;
import org.statistic.eggs.dialogs.FeedCompositionDialog;
import org.statistic.eggs.handler.ErrorHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

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
