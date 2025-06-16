module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.apache.commons.csv;


    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
}