package org.statistic.eggs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import org.statistic.eggs.core.dao.StatisticDao;
import org.statistic.eggs.core.entity.Counter;
import org.statistic.eggs.core.persistence.Persistence;
import org.statistic.eggs.core.views.DaysView;
import org.statistic.eggs.core.views.MonthView;
import org.statistic.eggs.core.views.StatisticView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Controller {

    // TODO: Implement manual add
    @FXML
    public TextField addManually;

    @FXML
    private Label prevResults;

    @FXML
    private TextField inputField;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Button toggleStatsButton;

    @FXML
    private ListView<String> listView;

    @FXML
    private TableView<Counter> tableView;

    @FXML
    private TableColumn<Counter, String> dayColumn;

    @FXML
    private TableColumn<Counter, Integer> amountColumn;

    @FXML
    private DatePicker datePicker;
    private boolean needRefresh = true;


    @FXML
    private void initialize() {
        showStatistic(StatisticView.DAILY);
        populateOptions();
        populateStatisticTable();
    }

    private void populateStatisticTable() {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    private void populateOptions() {
        if(needRefresh) {
            choiceBox.getItems().addAll("Monthly Statistic", "Days Statistic");
            choiceBox.setValue("– Select Statistic View –");
            choiceBox.setOnAction(event -> {
                if ("Monthly Statistic".equals(choiceBox.getValue())) {
                    showStatistic(StatisticView.MONTHLY);
                }
                if ("Days Statistic".equals(choiceBox.getValue())) {
                    showStatistic(StatisticView.DAILY);
                }
            });
            needRefresh = false;
        }
    }

    private void showStatistic(StatisticView statisticView) {
        lineChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(String.valueOf(LocalDateTime.now().getYear()));
        List<Counter> result = StatisticDao.getAllData();

        result.sort(Comparator.comparing(Counter::getDateTime));
        Map<Month, Integer> monthStatistic = calculateAmountByMonth(result);

        if (statisticView == StatisticView.MONTHLY) {
            monthStatistic.forEach((month, amount) -> {
                series.getData().add(new XYChart.Data<>(month.name(), amount));
            });
        }

        result.forEach(counter -> {
            if (statisticView == StatisticView.DAILY) {
                series.getData().add(new XYChart.Data<>(DaysView.getName(counter.getDateTime().getDayOfWeek().name()) + "("
                        + counter.getDateTime() + ")"
                        , counter.getAmount()));
            }
        });

        ObservableList<Counter> data = FXCollections.observableArrayList();
        result.sort(Collections.reverseOrder());
        data.addAll(result);
        tableView.setItems(data);
        lineChart.getData().add(series);

        for (XYChart.Data<String, Number> toolTipData : series.getData()) {
            Tooltip tooltip = new Tooltip("Amount: " + toolTipData.getYValue());
            Tooltip.install(toolTipData.getNode(), tooltip);
            toolTipData.getNode().setStyle("-fx-background-color: orange, white;");
        }
    }

    private static List<Counter> unitedAmountByDay(List<Counter> previous) {
        Map<LocalDate, Integer> withoutDateDuplicates = calculateSameDateAmount(previous);

        List<Counter> result = new ArrayList<>();
        withoutDateDuplicates.forEach((localDate, integer) -> {
            Counter counter = new Counter();
            counter.setAmount(integer);
            counter.setDateTime(localDate);
            result.add(counter);
        });
        return result;
    }

    private static Map<LocalDate, Integer> calculateSameDateAmount(List<Counter> previous) {
        Map<LocalDate, Integer> withoutDateDuplicates = new HashMap<>();
        for (Counter counter : previous) {
            withoutDateDuplicates.put(counter.getDateTime(), withoutDateDuplicates.getOrDefault(counter.getDateTime(), 0) + counter.getAmount());
        }
        return withoutDateDuplicates;
    }

    private static Map<Month, Integer> calculateAmountByMonth(List<Counter> previous) {
        Map<Month, Integer> amountByMonth = new HashMap<>();
        for (Counter counter : previous) {
            amountByMonth.put(counter.getDateTime().getMonth(), amountByMonth.getOrDefault(counter.getDateTime().getMonth(), 0) + counter.getAmount());
        }
        return amountByMonth;
    }

    @FXML
    private void onManualSaveButtonClick() {
        try {
            List<Counter> allData = new ArrayList<>(tableView.getItems());

            int amount = Integer.parseInt(addManually.getText());
            LocalDate date = datePicker.getValue();

            for (Counter counter : allData) {
                if (date.equals(counter.getDateTime())) {
                    showError("You trying  insert already existing record for date: " + date
                    + System.lineSeparator() +
                            "If You want update use update button");
                    return;
                } if(counter.getDateTime().isBefore(LocalDate.now())) {
                    showError("Impossible add value to the next day: " + date);
                    return;
                }
            }

            if (date == null) {
                showError("Please select a date.");
                return;
            }

            Counter entry = new Counter();
            entry.setAmount(amount);
            entry.setDateTime(date);

            Persistence<Counter> saver = new Persistence<>();
            saver.persist(entry);

            addManually.clear();
            datePicker.setValue(null);
            initialize();
        } catch (NumberFormatException e) {
            showError("Please enter a valid number of eggs.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onUpdate() {
        Counter selectedEntry = tableView.getSelectionModel().getSelectedItem();
        if (selectedEntry == null) {
            showError("Please select a row to update.");
            return;
        }
        try {
            int newAmount = Integer.parseInt(addManually.getText());
            addManually.clear();
            datePicker.setValue(null);
            if(newAmount > 0) {
                selectedEntry.setAmount(newAmount);
                selectedEntry.setDateTime(selectedEntry.getDateTime());
                tableView.refresh();
                StatisticDao.update(selectedEntry);
            } else {
                showError("You put value 0 ");
            }
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
        }
    }

    public void onDelete() {
        Counter selectedEntry = tableView.getSelectionModel().getSelectedItem();
        if (selectedEntry == null) {
            showError("Please select a row to delete.");
            return;
        }

        // Викликаємо вікно підтвердження
        boolean confirmed = showConfirmationDialog(
                "Confirm Deletion",
                "Are you sure you want to delete this entry?",
                "Date: " + selectedEntry.getDateTime() + "\nAmount: " + selectedEntry.getAmount()
        );

        if (confirmed) {
            try {
                StatisticDao.deleteByDate(selectedEntry.getDateTime());
                showMessage("Entry deleted:\nDate: " + selectedEntry.getDateTime() + "\nAmount: " + selectedEntry.getAmount());
            } catch (Exception e) {
                showError("Error deleting entry: " + e.getMessage());
            } finally {
                initialize();
                tableView.refresh();
            }
        }
    }

    private boolean showConfirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }


    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Entry was successful deleted");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}