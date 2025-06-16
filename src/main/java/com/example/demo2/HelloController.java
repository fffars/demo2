package com.example.demo2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.LinkedList;

import static com.example.demo2.HelloApplication.onStartCsv;

public class HelloController {

    File file;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Добро пожаловать в JavaFX приложение!");
    }

    @FXML
    protected void closeWindow() {
        System.exit(0);
    }

    @FXML
    protected void openCSV() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл CSV");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV файлы", "*.csv"));
        file = fileChooser.showOpenDialog(null);
        if (file == null)
        {
            return;
        }
        AreaChart<String, Integer> areaChart = (AreaChart<String, Integer>) HelloApplication.getFxmlLoader().getNamespace().get("areaChart");
        areaChart.getData().clear();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Данные из файла " + file.getName());
        Reader in = null;
        try {
            in = new FileReader(file.getAbsoluteFile().toString());
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
                series.getData().add(new XYChart.Data<>(String.valueOf(i + 1), value));
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        areaChart.getData().add(series);

    }

    @FXML
    protected void initialize() {
        Platform.runLater(() -> {
            onStartCsv();
        });

    }


    public File getFile() {
        return file;
    }
}