package fr.ul.miage.ikram.projet.controleur;

import fr.ul.miage.ikram.projet.modele.*;
import fr.ul.miage.ikram.projet.utilitaires.ConvertisseurTemps;
import fr.ul.miage.ikram.projet.utilitaires.ExportateurCsv;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.stage.FileChooser;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Controleur principal pour gérer les interactions utilisateur.
 */
public class ControleurPrincipal {

    /**
     * Bouton pour démarrer ou pauser la simulation.
     */
    @FXML
    private Button demarrerButton;

    /**
     * Bouton pour réinitialiser la simulation.
     */
    @FXML
    private Button resetButton;

    /**
     * Bouton pour éditer les paramètres des robinets.
     */
    @FXML
    private Button editerRobinetsButton;

    /**
     * Bouton pour exporter les données en CSV.
     */
    @FXML
    private Button exporterButton;

    /**
     * Bouton pour afficher le graphique.
     */
    @FXML
    private Button graphButton;

    /**
     * Label pour afficher la somme des débits des robinets.
     */
    @FXML
    private Label sommeRobinets;

    /**
     * Label pour afficher la somme des débits des fuites.
     */
    @FXML
    private Label sommeFuites;

    /**
     * Label pour afficher la quantité d'eau actuelle dans la baignoire.
     */
    @FXML
    private Label quantiteActuelle;

    /**
     * Label pour afficher l'heure actuelle de la simulation.
     */
    @FXML
    private Label heureActuelle;

    /**
     * Instance statique de la simulation.
     */
    private static final Simulation simulation = new Simulation();

    /**
     * Indique si la simulation est en pause.
     */
    private boolean isPaused = false;

    /**
     * Rectangle pour afficher visuellement le niveau d'eau dans la baignoire.
     */
    @FXML
    private Rectangle waterInBath;

    /**
     * La liste des sémaphores utilisés pour synchroniser les threads.
     */
    private List<Semaphore> semaphores;

    /**
     * Thread pour la tâche de mise à jour de l'interface utilisateur.
     */
    private Thread updateTaskThread;

    /**
     * Initialise le contrôleur principal.
     */
    @FXML
    private void initialize() {
        mettreAJourHeure();
        mettreAJourDetails();
        mettreAJourWaterInBath();
        resetButton.setDisable(true);
        exporterButton.setDisable(true);
        graphButton.setDisable(true);
    }

    /**
     * Démarre ou pause la simulation.
     */
    @FXML
    private void demarrerPauseSimulation() {
        if (simulation != null) {
            if (isPaused) {
                isPaused = false;
                demarrerButton.setText("Pause");
                simulation.resume();
                resetButton.setDisable(true); // Désactive le bouton de réinitialisation lorsque la simulation reprend
            } else if (semaphores == null) {
                isPaused = false;
                demarrerButton.setText("Pause");
                simulation.demarrer();
                semaphores = simulation.getSemaphores();
                updateTaskThread = new Thread(this::updateTask);
                updateTaskThread.start();
            } else {
                isPaused = true;
                demarrerButton.setText("Continuer");
                simulation.pause();
                resetButton.setDisable(false); // Active le bouton de réinitialisation lorsque la simulation est en pause
            }
        }
    }

    /**
     * Réinitialise la simulation.
     */
    @FXML
    private void resetSimulation() {
        if (simulation != null) {
            simulation.reset();
            if (updateTaskThread != null) {
                updateTaskThread.interrupt();
                try {
                    updateTaskThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            this.semaphores = null;
            this.isPaused = false;
            this.demarrerButton.setText("Commencer");
            this.resetButton.setDisable(true);
            this.exporterButton.setDisable(true);
            this.graphButton.setDisable(true);
            mettreAJourHeure();
            mettreAJourDetails();
            mettreAJourWaterInBath();
        }
    }

    /**
     * Ouvre la fenêtre pour éditer les paramètres des robinets.
     */
    @FXML
    private void editerRobinets() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/Parametres.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Paramètres");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 700, 550));

            stage.showAndWait();
            mettreAJourDetails();
            mettreAJourWaterInBath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour l'heure affichée.
     */
    private void mettreAJourHeure() {
        heureActuelle.setText(String.format("%02d:00", simulation.getHeure()));
    }

    /**
     * Met à jour les détails affichés (somme des débits, quantité d'eau, etc.).
     */
    private void mettreAJourDetails() {
        int totalDebitRobinets = simulation.getRobinets().stream().mapToInt(Robinet::getDebit).sum();
        int totalDebitFuites = simulation.getNonPassFuites().stream().mapToInt(Fuite::getDebit).sum();
        sommeRobinets.setText(String.valueOf(totalDebitRobinets));
        sommeFuites.setText(String.valueOf(totalDebitFuites));
        quantiteActuelle.setText(String.valueOf(simulation.getBaignoire().getNiveauEau()));
    }

    /**
     * Met à jour le niveau d'eau affiché dans le rectangle.
     */
    private void mettreAJourWaterInBath() {
        double capacite = simulation.getBaignoire().getCapacite();
        double niveauEau = simulation.getBaignoire().getNiveauEau();
        double pourcentage = niveauEau / capacite;

        // Définit la hauteur maximale basée sur la taille initiale du rectangle dans le FXML
        double maxHeight = 139.0;
        double newHeight = pourcentage * maxHeight;
        waterInBath.setHeight(newHeight);
        waterInBath.setTranslateY(maxHeight - newHeight); // Ajuste la position pour remplir de bas en haut
    }

    /**
     * Obtient l'instance de la simulation.
     *
     * @return l'instance de la simulation
     */
    public static Simulation getSimulation() {
        return simulation;
    }

    /**
     * Exporte les données de la simulation vers un fichier CSV.
     */
    @FXML
    private void exporterCsv() {
        if (simulation != null && !Simulation.getData().isEmpty()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer les données de simulation");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
            File file = fileChooser.showSaveDialog(exporterButton.getScene().getWindow());

            if (file != null) {
                List<String[]> data = new ArrayList<>();
                for (int[] entry : Simulation.getData()) {
                    data.add(new String[]{ConvertisseurTemps.convertirTemps(entry[0]), String.valueOf(entry[1])});
                }
                ExportateurCsv.exporter(data, file.getAbsolutePath());
            }
        }
    }

    /**
     * Affiche le graphique des données de la simulation.
     */
    @FXML
    private void afficherGraphique() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/Graphique.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Graphique de la Simulation");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 800, 600));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tâche de mise à jour pour gérer la synchronisation des threads et la mise à
     * jour de l'interface utilisateur.
     */
    private void updateTask() {
        int index = Simulation.threadIndex++;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // Acquiert le sémaphore pour ce thread
                semaphores.get(index).acquire();
                // Attente active tant que le thread est en pause
                while (isPaused) {
                    Thread.sleep(100); // Petit délai pour éviter une attente active trop agressive
                }
                // Mise à jour de l'interface utilisateur
                Platform.runLater(() -> {
                    if (simulation.getBaignoire().estPleine()) {
                        demarrerPauseSimulation();
                    }
                    mettreAJourHeure();
                    mettreAJourDetails();
                    mettreAJourWaterInBath();
                    // graphButton.setDisable(simulation.getData().isEmpty());
                    exporterButton.setDisable(simulation.getData().isEmpty());
                    graphButton.setDisable(simulation.getData().isEmpty());
                });
                // Libère le sémaphore pour le prochain thread
                semaphores.get((index + 1) % semaphores.size()).release();
                // Attend une seconde avant la prochaine itération
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Interrompt le thread en cas d'exception
                Thread.currentThread().interrupt();
                System.out.println(e.getMessage());
            }
        }
    }
}
