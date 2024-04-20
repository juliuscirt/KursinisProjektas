module com.example.kursinisprojektas {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires mysql.connector.j;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.kursinisprojektas to javafx.fxml;
    exports com.example.kursinisprojektas;
    opens com.example.kursinisprojektas.fxControllers to javafx.fxml, javafx.base;
    exports com.example.kursinisprojektas.fxControllers to javafx.fxml, javafx.base;
    opens com.example.kursinisprojektas.model to javafx.fxml, org.hibernate.orm.core, jakarta.persistence;
    exports com.example.kursinisprojektas.model to javafx.fxml, org.hibernate.orm.core, jakarta.persistence;
}