package com.company.furye;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HelloController {

    @FXML
    private TextField nMaxField; // n_max için text alanı

    @FXML
    private TextField iMaxField; // i_max için text alanı

    @FXML
    private TextField a0Field; // a0 için text alanı

    @FXML
    private TextField omegaField; // omega için text alanı

    @FXML
    private TextField deltaTField; // delta_t için text alanı

    @FXML
    private VBox aFieldsContainer; // a'lar için dinamik oluşturulan VBox

    @FXML
    private VBox bFieldsContainer; // b'ler için dinamik oluşturulan VBox

    @FXML
    private LineChart<Double, Double> lineChart;

    private int n_max; // n_max değeri
    private int i_max; // i_max değeri
    private double a0;
    private double omega;
    private double delta_t;

    @FXML
    private void plotButtonClicked() {
        double[] a = new double[n_max + 1];
        double[] b = new double[n_max + 1];

        // A ve B katsayılarını al
        for (int i = 1; i <= n_max; i++) {
            TextField aField = (TextField) aFieldsContainer.lookup("#a" + i + "Field");
            TextField bField = (TextField) bFieldsContainer.lookup("#b" + i + "Field");

            // Kullanıcı bir şeyler girmiş mi kontrol et
            if (!aField.getText().isEmpty() && !bField.getText().isEmpty()) {
                a[i] = Double.parseDouble(aField.getText());
                b[i] = Double.parseDouble(bField.getText());
            } else {
                // Eğer kullanıcı bir şey girmemişse, katsayıları 0 olarak kabul et
                a[i] = 0.0;
                b[i] = 0.0;
            }
        }

        // Önceki verileri temizle
        lineChart.getData().clear();

        // Yeni seriyi oluştur
        // Yeni seriyi oluştur
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for (int i = 1; i <= i_max; i++) {
            double x = (double) i * delta_t; // x değerini hesapla
            double y = X(i, a0, a, b); // x değerine karşılık gelen y değerini hesapla
            series.getData().add(new XYChart.Data<>(x, y)); // Seriyi grafiğe ekle
        }

// Seriyi grafiğe ekle
        lineChart.getData().add(series);


        // Verileri dosyaya yaz
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter("outputt.dat"))) {
            for (int i = 1; i <= i_max; i++) {
                double x = (double) i * delta_t;
                double y = X(i, a0, a, b);
                writer.write(x + "," + y + "\n");
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    // X fonksiyonu
    private double X(int i, double a0, double[] a, double[] b) {
        double result = a0 / 2.0;
        for (int n = 1; n <= n_max; ++n) {
            result += a[n] * Math.cos(n * omega * i * delta_t) + b[n] * Math.sin(n * omega * i * delta_t);
        }
        return result;
    }

    @FXML
    private void initialize() {
        nMaxField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                n_max = Integer.parseInt(newValue);
                addCoefficientFields();
            } catch (NumberFormatException e) {
                // Handle the case where input is not a valid integer
            }
        });

        iMaxField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                i_max = Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
                // Handle the case where input is not a valid integer
            }
        });

        deltaTField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                delta_t = Double.parseDouble(newValue);
            } catch (NumberFormatException e) {
                // Handle the case where input is not a valid double
            }
        });

        omegaField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                omega = Double.parseDouble(newValue);
            } catch (NumberFormatException e) {
                // Handle the case where input is not a valid double
            }
        });

        a0Field.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                a0 = Double.parseDouble(newValue);
            } catch (NumberFormatException e) {
                // Handle the case where input is not a valid double
            }
        });
    }

    private void addCoefficientFields() {
        aFieldsContainer.getChildren().clear();
        bFieldsContainer.getChildren().clear();
        for (int i = 1; i <= n_max; i++) {
            TextField aField = new TextField();
            aField.setId("a" + i + "Field");
            aField.setPromptText("a" + i);
            TextField bField = new TextField();
            bField.setId("b" + i + "Field");
            bField.setPromptText("b" + i);
            aFieldsContainer.getChildren().add(aField);
            bFieldsContainer.getChildren().add(bField);
        }
    }
}
