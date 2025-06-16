package com.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;

public class HelloApplication extends Application {

    static FXMLLoader fxmlLoader;
    @Override
    public void start(Stage stage) throws IOException {
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        stage.setTitle("Отображение графиков");
        stage.setResizable(false);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void onStartCsv() {
        AreaChart<String, Integer> areaChart = (AreaChart<String, Integer>) fxmlLoader.getNamespace().get("areaChart");
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Данные из файла \"test.csv\"");
        Reader in = null;
        try {
            in = new FileReader("./test.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Iterable<CSVRecord> records = null;
        try {
            records = CSVFormat.DEFAULT.parse(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LinkedList<CSVRecord> number_csv = new LinkedList<>();
        records.forEach(record -> {
            number_csv.add(record);
        });
        try {
            for (Integer i = 0; i < number_csv.get(0).size(); i++) {
                Integer value = Integer.parseInt(number_csv.get(0).get(i));
                series.getData().add(new XYChart.Data<>(String.valueOf(i+1), value));
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        areaChart.getData().add(series);
    }

    public static FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }
}