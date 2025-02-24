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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
        List<Counter> previous = StatisticDao.getAllData();
        List<Counter> result = unitedAmountByDay(previous);
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
                        + MonthView.getName(counter.getDateTime().getMonth().name()) + ")"
                        , counter.getAmount()));
            }
        });

        ObservableList<Counter> data = FXCollections.observableArrayList();
        result.sort(Collections.reverseOrder());
        data.addAll(result);
        tableView.setItems(data);
        lineChart.getData().add(series);
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
    protected void onSaveButtonClick() {
        Counter counter = new Counter();
        counter.setAmount(Integer.parseInt(inputField.getText()));
        counter.setDateTime(LocalDate.now());
        Persistence<Counter> saver = new Persistence<>();
        saver.persist(counter);
        showStatistic(StatisticView.DAILY);
        inputField.clear();
    }

    @FXML
    private void onManualSaveButtonClick() {
        try {
            int amount = Integer.parseInt(addManually.getText());
            LocalDate date = datePicker.getValue();

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
            LocalDate newDate = datePicker.getValue();

            if (newDate == null) {
                showError("Please select a date.");
                return;
            }

            selectedEntry.setAmount(newAmount);
            selectedEntry.setDateTime(newDate);

            tableView.refresh();

            addManually.clear();
            datePicker.setValue(null);
            StatisticDao.deleteByDate(newDate);
            if(newAmount > 0) {
                Persistence<Counter> saver = new Persistence<>();
                saver.persist(selectedEntry);
            }
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
        }
    }

    public void onDelete() {
        Counter selectedEntry = tableView.getSelectionModel().getSelectedItem();
        if (selectedEntry == null) {
            showError("Please select a row to update.");
            return;
        }
        try {
            StatisticDao.deleteByDate(selectedEntry.getDateTime());
            showMessage("Entry for date " + selectedEntry.getDateTime() +
                    System.lineSeparator() + "Amount " +
                    selectedEntry.getAmount() +
                    System.lineSeparator() +
                    "Was Deleted");
        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
        } finally {
            initialize();
            tableView.refresh();
        }
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Entry was successful deleted");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}