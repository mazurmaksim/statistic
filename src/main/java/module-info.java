module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;

    requires java.naming;

    requires java.sql;
    requires org.hibernate.orm.core;
    opens org.statistic.eggs.core.entity to org.hibernate.orm.core;
    exports org.statistic.eggs.core.entity;
    opens org.statistic.eggs to javafx.fxml;
    exports org.statistic.eggs;
    exports org.statistic.eggs.core.views;
    opens org.statistic.eggs.core.views to javafx.fxml;
    exports org.statistic.eggs.dialogs;
    opens org.statistic.eggs.dialogs to javafx.fxml;
}