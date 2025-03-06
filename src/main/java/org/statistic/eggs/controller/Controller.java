package org.statistic.eggs.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.statistic.eggs.core.dao.StatisticDao;
import org.statistic.eggs.core.entity.Counter;
import org.statistic.eggs.core.entity.FeedComposition;
import org.statistic.eggs.core.entity.WeatherForecast;
import org.statistic.eggs.core.forecast.WeatherParser;
import org.statistic.eggs.core.forecast.WeatherResponse;
import org.statistic.eggs.core.persistence.Persistence;
import org.statistic.eggs.core.service.WeatherService;
import org.statistic.eggs.core.views.DaysView;
import org.statistic.eggs.core.views.StatisticView;
import org.statistic.eggs.dialogs.FeedCompositionDialog;
import org.statistic.eggs.handler.ErrorHandler;

import java.io.IOException;
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

public class Controller {

    @FXML
    public TextField addManually;
    @FXML
    public LineChart<String, Number> historyChart;
    @FXML
    public Slider historySlider;
    @FXML
    public ChoiceBox<String> foodPlanChoice;
    @FXML
    public TableColumn<Counter, String> foodPlan;
    @FXML
    private TreeView<String> historyTree;
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TableView<Counter> tableView;
    @FXML
    private TableColumn<Counter, String> dayColumn;
    @FXML
    private TableColumn<Counter, Integer> amountColumn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private MenuBar menuBar;

    private boolean needRefresh = true;
    private int counter = 7;

    @FXML
    private void initialize() {
        try {
            populateComponents();
            populateCharts();
            populateOptions();
            populateStatisticTable();
            historyTree.setOnMouseClicked(this::handleTreeClick);
            manipulateSlider();
        } catch (Exception e) {
            ErrorHandler.showErrorDialog("Error happened while starting application",e);
        }
    }

    private WeatherResponse getWeatherForecast() {
       try {
           WeatherParser parser = new WeatherParser();
           return parser.parseWeatherJson(WeatherService.getWeather());
       } catch (Exception ex) {
           ErrorHandler.showErrorDialog("Could not get weather forecast from API", ex);
       }
       return null;
    }

    private void populateCharts() {
        if (choiceBox.getValue() != null) {
            String selectedValue = choiceBox.getValue();

            switch (selectedValue) {
                case "Weeks Statistic":
                    showStatistic(StatisticView.WEEKS);
                    break;
                case "Monthly Statistic":
                    showStatistic(StatisticView.MONTHLY);
                    break;
                case "Yearly Statistic":
                    showStatistic(StatisticView.YEARLY);
                    break;
                default:
                    showStatistic(StatisticView.DAILY);
                    break;
            }
        } else {
            showStatistic(StatisticView.DAILY);
        }
    }

    private void populateStatisticTable() {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        foodPlan.setCellValueFactory(cellData -> {
            Counter counter = cellData.getValue();
            FeedComposition feedComposition = counter.getFeedComposition();
            return new SimpleStringProperty(feedComposition != null ? feedComposition.getName() : "N/A");
        });
    }

    private void populateComponents() {
        foodPlan.setCellFactory(tc -> new TableCell<Counter, String>() {
            private final Hyperlink link = new Hyperlink();

            {
                link.setOnAction(event -> {
                    Counter counter = getTableView().getItems().get(getIndex());
                    FeedComposition feedComposition = counter.getFeedComposition();
                    if (feedComposition != null) {
                        showFoodPlanDetails(feedComposition);
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    link.setText(item);
                    setGraphic(link);
                }
            }
        });

    }

    private void showFoodPlanDetails(FeedComposition feedComposition) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/statistic/eggs/foodPlanDetails.fxml"));
            Parent root = loader.load();

            FoodPlanController controller = loader.getController();
            controller.setFoodComposition(feedComposition);

            Stage stage = new Stage();
            stage.setTitle("Food Plan Details");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateOptions() {
        if(needRefresh) {
            choiceBox.getItems().addAll("Days Statistic", "Weeks Statistic", "Monthly Statistic", "Yearly Statistic" );
            choiceBox.setValue("– Select Statistic View –");
            choiceBox.setOnAction(event -> {
                if ("Monthly Statistic".equals(choiceBox.getValue())) {
                    showStatistic(StatisticView.MONTHLY);
                }
                if ("Days Statistic".equals(choiceBox.getValue())) {
                    showStatistic(StatisticView.DAILY);
                }
                if ("Weeks Statistic".equals(choiceBox.getValue())) {
                    showStatistic(StatisticView.WEEKS);
                }
                if ("Yearly Statistic".equals(choiceBox.getValue())) {
                    showStatistic(StatisticView.YEARLY);
                }
            });
            needRefresh = false;
        }
    }

    private void showStatistic(StatisticView statisticView) {
        try {
            lineChart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(String.valueOf(LocalDateTime.now().getYear()));
            List<Counter> result = StatisticDao.getStatisticData();
            result.sort(Comparator.comparing(Counter::getDateTime));

            List<FeedComposition> feedCompositions = StatisticDao.getFeedComposition();
            foodPlanChoice.getItems().clear();
            feedCompositions.forEach(f -> {
                foodPlanChoice.getItems().add(f.getName());
                if (f.isActive()) {
                    foodPlanChoice.setValue(f.getName());
                }
            });

            historySlider.setMax(result.size() - counter);
            addHistoryRecord(result);
            if (statisticView == StatisticView.YEARLY) {
                historySlider.setVisible(false);
                Map<Integer, Integer> monthStatistic = calculateAmountByYear(result);
                SortedMap<Integer, Integer> sortedMonthStatistic = new TreeMap<>(monthStatistic);
                sortedMonthStatistic.forEach((year, amount) -> {
                    series.getData().add(new XYChart.Data<>(year.toString(), amount));
                });
                lineChart.getData().add(series);
            }

            if (statisticView == StatisticView.MONTHLY) {
                lineChart.getData().clear();
                historySlider.setVisible(false);
                Map<Month, Integer> monthStatistic = calculateAmountByMonth(result);
                SortedMap<Month, Integer> sortedMonthStatistic = new TreeMap<>(monthStatistic);
                sortedMonthStatistic.forEach((month, amount) -> {
                    series.getData().add(new XYChart.Data<>(month.name(), amount));
                });
                lineChart.getData().add(series);
            }

            if (statisticView == StatisticView.WEEKS) {
                historySlider.setVisible(false);
                lineChart.getData().clear();
                Map<Integer, Integer> monthsStatistic = calculateAmountByWeek(result);
                Map<Integer, Integer> weeksStatistic = new HashMap<>();
                LocalDate today = LocalDate.now();
                YearMonth currentMonth = YearMonth.of(today.getYear(), today.getMonth());
                int daysInMonth = currentMonth.lengthOfMonth();
                int firstDayOfWeek = currentMonth.atDay(1).getDayOfWeek().getValue();

                for (int day = 1; day <= daysInMonth; day++) {
                    int weekNumber = (day + firstDayOfWeek - 2) / 7;
                    weeksStatistic.put(weekNumber, weeksStatistic.getOrDefault(weekNumber, 0) + monthsStatistic.getOrDefault(day, 0));
                }
                SortedMap<Integer, Integer> sortedWeeksStatistic = new TreeMap<>(weeksStatistic);
                sortedWeeksStatistic.forEach((week, amount) -> {
                    if (week != 0 && amount != 0) {
                        series.getData().add(new XYChart.Data<>("Week " + (week), amount));
                    }
                });
                lineChart.getData().add(series);
            }

            if (statisticView == StatisticView.DAILY) {
                showDailyStEggsWeather(result);
            }

            ObservableList<Counter> data = FXCollections.observableArrayList();
            result.sort(Collections.reverseOrder());
            data.addAll(result);
            tableView.setItems(data);

            populateEggAmountChartNodes(series, result);
        } catch (Exception e) {
            ErrorHandler.showErrorDialog("Could not load statistic", e);
        }
    }

    private Map<Integer, Integer> calculateAmountByYear(List<Counter> previous) {
        Map<Integer, Integer> amountByMonth = new HashMap<>();
        for (Counter counter : previous) {
            amountByMonth.put(counter.getDateTime().getYear(), amountByMonth.getOrDefault(counter.getDateTime().getYear(), 0) + counter.getAmount());
        }
        return amountByMonth;
    }

    private Map<Integer, Integer> calculateAmountByWeek(List<Counter> previous) {
        Map<Integer, Integer> amountByMonth = new HashMap<>();
        for (Counter counter : previous) {
            if(counter.getDateTime().getMonth().equals(LocalDate.now().getMonth()) && counter.getDateTime().getYear() == LocalDate.now().getYear()) {
                amountByMonth.put(counter.getDateTime().getDayOfMonth(), amountByMonth.getOrDefault(counter.getDateTime().getDayOfMonth(), 0) + counter.getAmount());
            }
        }
        return amountByMonth;
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
            if (counter.getDateTime().getYear() == LocalDate.now().getYear())
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
            String foodCompositionName = foodPlanChoice.getValue();
            Counter entry = new Counter();

            WeatherResponse weatherResponse = getWeatherForecast();
            WeatherForecast weatherForecast = new WeatherForecast();

            if(weatherResponse !=null) {
                weatherForecast.setHumidity(weatherResponse.getMain().getHumidity());
                weatherForecast.setTemperature(weatherResponse.getMain().getTemp());
                weatherForecast.setWindSpeed(weatherResponse.getWind().getSpeed());
                weatherForecast.setRetrievedSuccessfully(true);
            }

            if (date == null) {
                showError("Please select a date.");
                return;
            }
            FeedComposition feedComposition = StatisticDao.getFeedCompositionByName(foodCompositionName);
            for (Counter counter : allData) {
                if(counter.getWeatherForecast() == null) {
                    entry.setWeatherForecast(weatherForecast);
                }
                if (date.equals(counter.getDateTime())) {
                    showError("You trying  insert already existing record for date: " + date
                    + System.lineSeparator() +
                            "If You want update use update button");
                    return;
                }
            }

            if (date.isAfter(LocalDate.now())) {
                showError("Impossible to add value to a past day: " + date);
                return;
            }

            entry.setAmount(amount);
            entry.setDateTime(date);
            entry.setFeedComposition(feedComposition);
            Persistence<Counter> saver = new Persistence<>();
            weatherForecast.setDayStatistic(entry);

            saver.persist(entry);

            addManually.clear();
            datePicker.setValue(null);
            initialize();
        } catch (NumberFormatException e) {
            showError("Please enter a valid number of eggs.");
        } catch (Exception ex) {
            showError(ex.getMessage());
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
                int amount = newAmount + selectedEntry.getAmount();
                selectedEntry.setAmount(amount);
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

    public void addHistoryRecord(List<Counter> allStatistic) {
        if (historyTree.getRoot() == null) {
            historyTree.setRoot(new TreeItem<>("History"));
        }
        TreeItem<String> root = historyTree.getRoot();

        for (Counter counter : allStatistic) {
            LocalDate date = counter.getDateTime();
            int year = date.getYear();
            Month month = date.getMonth();

            TreeItem<String> yearNode = getOrCreateNode(root, String.valueOf(year));
            getOrCreateNode(yearNode, month.name());
        }
    }

    private TreeItem<String> getOrCreateNode(TreeItem<String> parent, String value) {
        return parent.getChildren().stream()
                .filter(node -> node.getValue().equals(value))
                .findFirst()
                .orElseGet(() -> {
                    TreeItem<String> newNode = new TreeItem<>(value);
                    parent.getChildren().add(newNode);
                    return newNode;
                });
    }

    private void handleTreeClick(MouseEvent event) {
        TreeItem<String> selectedItem = historyTree.getSelectionModel().getSelectedItem();
        if (selectedItem == null || selectedItem.getParent() == null) return;

        TreeItem<String> yearNode = selectedItem.getParent();
        if (yearNode == null) return;
        try {
            int year = Integer.parseInt(yearNode.getValue());
            Month month = Month.valueOf(selectedItem.getValue());
            showStatisticForMonth(year, month);
        } catch (NumberFormatException ignore) {

        }
    }

//    TODO: add weather statistic
    private void showStatisticForMonth(int year, Month month) {
        historyChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(String.valueOf(LocalDateTime.now().getYear()));
        List<Counter> allData = StatisticDao.getStatisticData();

        List<Counter> result = allData.stream()
                .filter(f -> f.getDateTime().getYear() == year && f.getDateTime().getMonth().equals(month))
                .sorted(Comparator.comparing(Counter::getDateTime))
                .toList();

        result.forEach(counter -> {
            series.getData().add(new XYChart.Data<>(
                    DaysView.getName(counter.getDateTime().getDayOfWeek().name()) + " (" + counter.getDateTime() + ")",
                    counter.getAmount()
            ));
        });

        historyChart.getData().add(series);
        populateEggAmountChartNodes(series, result);
    }

    @FXML
    private void showAddFoodDialog() {
        FeedCompositionDialog dialog = new FeedCompositionDialog();
        Stage stage = (Stage) menuBar.getScene().getWindow();
        dialog.showDialog(stage);
    }

    private void manipulateSlider() {
        Tooltip tooltipSlider = new Tooltip("Slide to see dynamic");
        Tooltip.install(historySlider, tooltipSlider);
        historySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int diff = newVal.intValue() - oldVal.intValue();
            counter += diff;
            lineChart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(String.valueOf(LocalDateTime.now().getYear()));

            List<Counter> result = StatisticDao.getStatisticData();
            Collections.sort(result);
            List<Counter> filteredData = new ArrayList<>(result.stream()
                    .skip(Math.max(0, result.size() - counter))
                    .toList());

            showDailyStEggsWeather(result);

            ObservableList<Counter> data = FXCollections.observableArrayList(filteredData);
            tableView.setItems(data);

            populateEggAmountChartNodes(series, result);
        });
    }

    private void showDailyStEggsWeather(List<Counter> result) {
        XYChart.Series<String, Number> eggSeries = new XYChart.Series<>();
        eggSeries.setName("Кількість яєць");

        XYChart.Series<String, Number> tempSeries = new XYChart.Series<>();
        tempSeries.setName("Температура (°C)");

        result.stream()
                .skip(Math.max(0, result.size() - counter))
                .forEach(stat -> {
                        lineChart.getData().clear();
                        if (stat.getDateTime().getYear() == LocalDate.now().getYear()) {
                            // Get the date in the required format
                            String dateLabel = DaysView.getName(stat.getDateTime().getDayOfWeek().name()) +
                                    "(" + stat.getDateTime() + ")";

                            // Adding data on eggs
                            eggSeries.getData().add(new XYChart.Data<>(dateLabel, stat.getAmount()));

                            // Get the temperature for this date (if available)
                            WeatherForecast weather = stat.getWeatherForecast();
                            if(weather !=null) {
                                tempSeries.getData().add(new XYChart.Data<>(dateLabel, weather.getTemperature()));
                            }
                        }
                        lineChart.getData().addAll(eggSeries, tempSeries);
                        populateEggAmountChartNodes(eggSeries, result);
                        populateWeatherChartNodes(tempSeries, result);
                });
    }

    private static void populateEggAmountChartNodes(XYChart.Series<String, Number> series, List<Counter> result) {
        for (XYChart.Data<String, Number> toolTipData : series.getData()) {
            Optional<Counter> counterOptional = result.stream()
                    .filter(c -> c.getAmount().equals(toolTipData.getYValue()))
                    .findFirst();

            String tooltipText = "Amount: " + toolTipData.getYValue();

            if (counterOptional.isPresent() && counterOptional.get().getFeedComposition() != null) {
                FeedComposition feedComposition = counterOptional.get().getFeedComposition();
                tooltipText += "\nFeed: " + feedComposition.getName() + "\nDate: " + feedComposition.getDate();
            }

            Tooltip tooltip = new Tooltip(tooltipText);
            Tooltip.install(toolTipData.getNode(), tooltip);
            toolTipData.getNode().setStyle("-fx-background-color: orange, white;");
        }
    }

  private static void populateWeatherChartNodes(XYChart.Series<String, Number> series, List<Counter> result) {
        for (XYChart.Data<String, Number> toolTipData : series.getData()) {
            Optional<Counter> weatherOptional = result.stream()
                    .filter(c -> c.getWeatherForecast()!= null && c.getWeatherForecast().getTemperature().equals(toolTipData.getYValue()))
                    .findFirst();

            String tooltipText = "Температура °C " + toolTipData.getYValue();

            if (weatherOptional.isPresent() && weatherOptional.get().getWeatherForecast() != null) {
                WeatherForecast weatherForecast = weatherOptional.get().getWeatherForecast();
                tooltipText += "\nВологість повітря: " + weatherForecast.getHumidity() + "\nШвидкість вітру: " + weatherForecast.getWindSpeed();
            }

            Tooltip tooltip = new Tooltip(tooltipText);
            Tooltip.install(toolTipData.getNode(), tooltip);
            toolTipData.getNode().setStyle("-fx-background-color: orange, white;");
        }
    }

    @FXML
    private void showWeatherSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/statistic/eggs/weatherSettings.fxml"));
            Parent root = loader.load();

            WeatherSettingsController controller = loader.getController();
//            controller.setFoodComposition(feedComposition);

            Stage stage = new Stage();
            stage.setTitle("Weather Forecast Settings");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}