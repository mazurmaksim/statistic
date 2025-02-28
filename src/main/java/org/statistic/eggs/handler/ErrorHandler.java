package org.statistic.eggs.handler;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorHandler {
    public static void showErrorDialog(Exception ex) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Happened Error:");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);

        TextArea textArea = new TextArea(sw.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        VBox dialogPaneContent = new VBox();
        dialogPaneContent.getChildren().add(textArea);

        alert.getDialogPane().setContent(dialogPaneContent);
        alert.showAndWait();
    }
}

