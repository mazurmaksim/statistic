package org.statistic.eggs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import org.statistic.eggs.core.dao.StatisticDao;
import org.statistic.eggs.core.entity.Counter;
import org.statistic.eggs.core.views.DaysView;
import org.statistic.eggs.core.views.MonthView;
import org.statistic.eggs.core.views.StatisticView;
import org.statistic.eggs.core.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.*;
import java.util.*;

public class Controller {
    @FXML
    private Label result;

    @FXML
    private Label prevResults;

    @FXML
    private TextField inputField;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Button toggleStatsButton;

    @FXML
    private ListView<String> listView;

    private final ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        listView.setItems(items);
        showStatistic(StatisticView.DAILY);
        choiceBox.getItems().addAll("Monthly Statistic", "Days Statistic");
        choiceBox.setValue("– Select View to Show –");
        choiceBox.setOnAction(event -> {
            if ("Monthly Statistic".equals(choiceBox.getValue())) {
                showStatistic(StatisticView.MONTHLY);
            }
            if ("Days Statistic".equals(choiceBox.getValue())) {
                showStatistic(StatisticView.DAILY);
            }
        });
    }

    private void showStatistic(StatisticView statisticView) {
        barChart.getData().clear();
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

        barChart.getData().add(series);
        result.sort(Collections.reverseOrder());
        items.clear();
        for (Counter h : result) {
            String day = DaysView.getName(h.getDateTime().getDayOfWeek().name());
            if (day != null && day.equals(DaysView.getName(LocalDate.now().getDayOfWeek().name()))) {
                day = DaysView.getName("TODAY");
            }
            items.add(day + ": " + h.getAmount() + " eggs");
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
    protected void onSaveButtonClick() {
        Counter counter = new Counter();
        counter.setAmount(Integer.parseInt(inputField.getText()));
        counter.setDateTime(LocalDate.now());
        Persistence<Counter> saver = new Persistence<>();
        saver.persist(counter);
        result.setText("Today saved value: " + inputField.getText());
        showStatistic(StatisticView.DAILY);
    }
}