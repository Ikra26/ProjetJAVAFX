package fr.ul.miage.ikram.projet.controleur;

import fr.ul.miage.ikram.projet.modele.Simulation;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class GraphiqueControleur {

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private void initialize() {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Niveau d'Eau");

        for (int[] entry : Simulation.getData()) {
            series.getData().add(new XYChart.Data<>(entry[1], entry[0]));
        }

        lineChart.getData().add(series);
    }
}
