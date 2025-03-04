package org.statistic.eggs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.statistic.eggs.core.dao.StatisticDao;
import org.statistic.eggs.core.entity.Settings;
import org.statistic.eggs.core.entity.WeatherSettings;

import java.util.Arrays;
import java.util.List;

public class WeatherSettingsController {

    @FXML
    private ComboBox<String> cityComboBox;

    @FXML
    private TextField apiKeyTextField;

    @FXML
    private void saveSettings() {
        String city = cityComboBox.getValue();
        String apiKey = apiKeyTextField.getText();

        if (city == null || city.isEmpty() || apiKey.isEmpty()) {
            System.out.println("Помилка: Всі поля мають бути заповнені.");
            return;
        }

        List<Settings> settings = StatisticDao.getAllSettings();
        Settings newSettings = null;
        if(settings.isEmpty()) {
            populateWeatherSettings(city, apiKey, newSettings);
        } else {
            for (Settings s : settings) {
                if (s.getWeatherSettings() == null) {
                    populateWeatherSettings(city, apiKey, newSettings);
                }
            }
        }

        // close window
        Stage stage = (Stage) cityComboBox.getScene().getWindow();
        stage.close();
    }

    private static void populateWeatherSettings(String city, String apiKey, Settings newSettings) {
        newSettings = new Settings();
        WeatherSettings weatherSettings = new WeatherSettings();
        weatherSettings.setCity(city);
        weatherSettings.setApiKey(apiKey);
        weatherSettings.setSettings(newSettings);

        newSettings.setWeatherSettings(weatherSettings);
        StatisticDao.saveWeatherSettings(newSettings);
    }

    @FXML
    private void initialize() {
        List<String> cities = Arrays.asList(
                "Kyiv", "Kharkiv", "Odesa", "Dnipro", "Donetsk", "Zaporizhzhia", "Lviv", "Kryvyi Rih", "Mykolaiv", "Sevastopol",
                "Kherson", "Vinnytsia", "Poltava", "Chernihiv", "Cherkasy", "Zhytomyr", "Sumy", "Rivne", "Ivano-Frankivsk",
                "Ternopil", "Lutsk", "Uzhhorod", "Khmelnytskyi", "Kropyvnytskyi", "Chernivtsi",
                "Mariupol", "Kamianske", "Bila Tserkva", "Kremenchuk", "Melitopol", "Kramatorsk", "Nikopol", "Sloviansk",
                "Berdiansk", "Pavlohrad", "Uman", "Brovary", "Mukachevo", "Kolomyia", "Kovel", "Oleksandriia", "Chornomorsk",
                "Drohobych", "Konotop", "Shostka", "Berdychiv", "Fastiv", "Irpin", "Vyshhorod", "Netishyn", "Boyarka", "Yuzhnoukrainsk"
        );
        cityComboBox.getItems().addAll(cities);
        cityComboBox.setValue("Kyiv");
    }
}