module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;

    requires java.naming;

    requires java.sql;
    requires org.hibernate.orm.core;
    opens org.statistic.eggs.entity to org.hibernate.orm.core;
    exports org.statistic.eggs.entity;
    opens org.statistic.eggs to javafx.fxml;
    exports org.statistic.eggs;
}