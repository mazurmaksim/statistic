package org.statistic.eggs;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.statistic.eggs.core.entity.Counter;
import org.statistic.eggs.persistence.Persistence;
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
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Button toggleStatsButton;

    @FXML
    private void initialize() {
        showStatistic(StatisticView.DAILY);
    }

    private void showStatistic(StatisticView statisticView) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(String.valueOf(LocalDateTime.now().getYear()));

        try (SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory()) {

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                List<Counter> previous = new ArrayList<Counter>(session.createQuery(" from Counter c ").list().stream().toList());

                List<Counter> result = unitedAmountByDay(previous);

                result.sort(Comparator.comparing(Counter::getDateTime));

                StringBuilder history = new StringBuilder();
                Map<Month, Integer> monthStatistic = calculateAmountByMonth(result);

                if (statisticView == StatisticView.MONTHLY) {
                    monthStatistic.forEach((month, amount) -> {
                        series.getData().add(new XYChart.Data<>(month.name(), amount));
                    });
                }

                result.forEach(counter -> {
                    if (statisticView == StatisticView.DAILY) {
                        series.getData().add(new XYChart.Data<>(counter.getDateTime().getDayOfWeek() + "("
                                + counter.getDateTime().getMonth().name() + ")"
                                , counter.getAmount()));
                    }
                });

                barChart.getData().add(series);
                if (!result.isEmpty() && result.size() >= 3) {
                    for (int i = 0; i < 3; i++) {
                        populate(history, result, i);
                    }
                    prevResults.setText(history + System.lineSeparator() + " Total amount from date " +
                            previous.get(0).getDateTime() + " is - " +
                            calculateTotalSum(previous));
                } else if (result.size() == 1) {
                    populate(history, result, 0);
                    prevResults.setText(history.toString());
                    prevResults.setText(" Total amount from " + previous.get(0).getDateTime() + ": " +
                            calculateTotalSum(previous));
                }
            } catch (Exception e) {
                System.err.println("Transaction failed: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("SessionFactory creation failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
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

    private static void populate(StringBuilder history, List<Counter> previous, int i) {
        i = previous.size() - i - 1;
        history.append(
                        previous.get(i)
                                .getDateTime()
                                .getDayOfWeek().equals(LocalDateTime.now().getDayOfWeek()) ? "Today " : previous.get(i)
                                .getDateTime()
                                .getDayOfWeek())
                .append(" ")
                .append(previous.get(i)
                        .getDateTime()
                        .getDayOfMonth())
                .append(".")
                .append(previous.get(i)
                        .getDateTime().getMonth().getValue())
                .append(", ")
                .append(" Amount: ")
                .append(previous.get(i)
                        .getAmount())
                .append(System.lineSeparator());
    }

    private static Integer calculateTotalSum(List<Counter> list) {
        Integer result = 0;
        for (Counter ctr : list) {
            result += ctr.getAmount();
        }
        return result;
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

    public void onViewMonthlyStatsClick() {
            showStatistic(StatisticView.MONTHLY);
            toggleStatsButton.setText("View Mothly Stats");
        }

    public void onViewDailyStatsClick() {
        showStatistic(StatisticView.DAILY);
        toggleStatsButton.setText("View Daily Stats");
    }
}