package org.statistic.eggs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.statistic.eggs.core.dao.StatisticDao;
import org.statistic.eggs.core.entity.ChickensSettings;
import org.statistic.eggs.core.entity.Settings;
import org.statistic.eggs.handler.ErrorHandler;

import java.util.List;

public class ChickensSettingController {

    @FXML
    public TextField chickenAmount;


    @FXML
    public void initialize() {
        getAmountSettings();
    }

    private void getAmountSettings() {
        List<Settings> settings = StatisticDao.getAllSettings();
        settings.forEach(s->{
            if(s.getChickenSettingsSettings() != null) {
                chickenAmount.setText(settings.get(0).getChickenSettingsSettings().getChickenAmount().toString());
            }
        });
    }

    @FXML
    private void saveChickenSettings() {
        String amount = chickenAmount.getText();
        try {
            List<Settings> settings = StatisticDao.getAllSettings();
            ChickensSettings chickensSettings = new ChickensSettings();

            settings.forEach(s->{
                if(s.getChickenSettingsSettings() == null) {
                    Integer intAmount = Integer.parseInt(amount);
                    chickensSettings.setChickenAmount(intAmount);
                    s.setChickenSettings(chickensSettings);
                } else {
                    chickensSettings.setId(settings.get(0).getId());
                    chickenAmount.setText(chickensSettings.getChickenAmount().toString());
                }
            });
            chickensSettings.setSettings(settings.get(0));
            StatisticDao.saveSettings(settings.get(0));
        } catch (NumberFormatException ex) {
            ErrorHandler.showErrorDialog("Please put number",ex);
        }
    }


    private static void populateChickenSettings(String amount) {
        Settings newSettings = new Settings();
        ChickensSettings chickensSettings = new ChickensSettings();

        newSettings.setChickenSettings(chickensSettings);
        StatisticDao.saveSettings(newSettings);
    }
}
