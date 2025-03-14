module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;

    requires java.naming;

    requires org.hibernate.orm.core;
    requires com.fasterxml.jackson.databind;
    requires jdk.httpserver;
    requires java.net.http;
    exports org.statistic.eggs.core.forecast;
    opens org.statistic.eggs.core.entity to org.hibernate.orm.core;
    exports org.statistic.eggs.core.entity;
    opens org.statistic.eggs to javafx.fxml;
    exports org.statistic.eggs;
    exports org.statistic.eggs.core.views;
    opens org.statistic.eggs.core.views to javafx.fxml;
    exports org.statistic.eggs.dialogs;
    opens org.statistic.eggs.dialogs to javafx.fxml;
    exports org.statistic.eggs.controller;
    opens org.statistic.eggs.controller to javafx.fxml;
}