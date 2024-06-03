package fr.ul.miage.ikram.projet.controleur;

import fr.ul.miage.ikram.projet.modele.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

/**
 * Controleur pour les paramètres de la simulation.
 */
public class ParametresControleur {

    /**
     * TableView pour afficher les robinets.
     */
    @FXML
    private TableView<Robinet> tableRobinets;

    /**
     * Colonne pour afficher l'index des robinets.
     */
    @FXML
    private TableColumn<Robinet, Integer> colIndexRobinet;

    /**
     * Colonne pour afficher le débit des robinets.
     */
    @FXML
    private TableColumn<Robinet, Integer> colDebitRobinet;

    /**
     * TableView pour afficher les fuites.
     */
    @FXML
    private TableView<Fuite> tableFuites;

    /**
     * Colonne pour afficher l'index des fuites.
     */
    @FXML
    private TableColumn<Fuite, Integer> colIndexFuite;

    /**
     * Colonne pour afficher le débit des fuites.
     */
    @FXML
    private TableColumn<Fuite, Integer> colDebitFuite;

    /**
     * Champ de texte pour afficher et modifier la capacité de la baignoire.
     */
    @FXML
    private TextField capaciteBaignoire;

    /**
     * Liste observable pour les robinets.
     */
    private ObservableList<Robinet> robinetsObservable;

    /**
     * Liste observable pour les fuites.
     */
    private ObservableList<Fuite> fuitesObservable;

    /**
     * Bouton pour ajouter un nouveau robinet.
     */
    @FXML
    private Button ajouterRobinetButton;

    /**
     * Bouton pour modifier un robinet existant.
     */
    @FXML
    private Button modifierRobinetButton;

    /**
     * Bouton pour supprimer un robinet existant.
     */
    @FXML
    private Button supprimerRobinetButton;

    /**
     * Bouton pour ajouter une nouvelle fuite.
     */
    @FXML
    private Button ajouterFuiteButton;

    /**
     * Bouton pour modifier une fuite existante.
     */
    @FXML
    private Button modifierFuiteButton;

    /**
     * Bouton pour supprimer une fuite existante.
     */
    @FXML
    private Button supprimerFuiteButton;

    /**
     * Bouton pour modifier la capacité de la baignoire.
     */
    @FXML
    private Button modifierCapaciteBaignoireButton;


    /**
     * Initialise le contrôleur des paramètres.
     */
    @FXML
    private void initialize() {
        // Initialise les listes de robinets et fuites
        this.setRobinets(ControleurPrincipal.getSimulation().getRobinets());
        this.setFuites(ControleurPrincipal.getSimulation().getNonPassFuites());

        // Configure les colonnes de la table des robinets
        colIndexRobinet.setCellValueFactory(new PropertyValueFactory<>("idRobinet"));
        colDebitRobinet.setCellValueFactory(new PropertyValueFactory<>("debit"));

        // Configure les colonnes de la table des fuites
        colIndexFuite.setCellValueFactory(new PropertyValueFactory<>("idFuite"));
        colDebitFuite.setCellValueFactory(new PropertyValueFactory<>("debit"));

        // Affiche la capacité actuelle de la baignoire
        capaciteBaignoire.setText(String.valueOf(ControleurPrincipal.getSimulation().getBaignoire().getCapacite()));

        // Désactive les boutons d'ajout et de modification des robinets et des fuites si les données de simulation ne sont pas vides
        ajouterRobinetButton.setDisable(!Simulation.getData().isEmpty());
        supprimerRobinetButton.setDisable(!Simulation.getData().isEmpty());
        ajouterFuiteButton.setDisable(!Simulation.getData().isEmpty());
        modifierFuiteButton.setDisable(!Simulation.getData().isEmpty());
    }

    /**
     * Définit la liste des robinets et met à jour la table correspondante.
     *
     * @param robinets la liste des robinets
     */
    public void setRobinets(List<Robinet> robinets) {
        robinetsObservable = FXCollections.observableArrayList(robinets);
        tableRobinets.setItems(robinetsObservable);
    }

    /**
     * Définit la liste des fuites et met à jour la table correspondante.
     *
     * @param fuites la liste des fuites
     */
    public void setFuites(List<Fuite> fuites) {
        fuitesObservable = FXCollections.observableArrayList(fuites);
        tableFuites.setItems(fuitesObservable);
    }

    /**
     * Ajoute un nouveau robinet à la simulation.
     */
    @FXML
    private void ajouterRobinet() {
        Robinet nouveauRobinet = new Robinet(10, ControleurPrincipal.getSimulation().getBaignoire());
        ControleurPrincipal.getSimulation().addRobinet(nouveauRobinet);
        robinetsObservable.add(nouveauRobinet);
    }

    /**
     * Modifie le débit du robinet sélectionné.
     */
    @FXML
    private void modifierRobinet() {
        Robinet selectedRobinet = tableRobinets.getSelectionModel().getSelectedItem();
        if (selectedRobinet != null) {
            TextInputDialog dialog = new TextInputDialog(String.valueOf(selectedRobinet.getDebit()));
            dialog.setTitle("Modifier Robinet");
            dialog.setHeaderText("Modifier le débit du robinet sélectionné");
            dialog.setContentText("Débit (L/s):");

            dialog.showAndWait().ifPresent(value -> {
                selectedRobinet.setDebit(Integer.parseInt(value));
                tableRobinets.refresh();
            });
        }
    }

    /**
     * Supprime le robinet sélectionné de la simulation.
     */
    @FXML
    private void supprimerRobinet() {
        Robinet selectedRobinet = tableRobinets.getSelectionModel().getSelectedItem();
        if (selectedRobinet != null) {
            selectedRobinet.arreter();
            robinetsObservable.remove(selectedRobinet);
            ControleurPrincipal.getSimulation().getRobinets().remove(selectedRobinet);
        }
    }

    /**
     * Ajoute une nouvelle fuite à la simulation.
     */
    @FXML
    private void ajouterFuite() {
        Fuite nouvelleFuite = new Fuite(10, ControleurPrincipal.getSimulation().getBaignoire());
        ControleurPrincipal.getSimulation().addFuite(nouvelleFuite);
        fuitesObservable.add(nouvelleFuite);
    }

    /**
     * Modifie le débit de la fuite sélectionnée.
     */
    @FXML
    private void modifierFuite() {
        Fuite selectedFuite = tableFuites.getSelectionModel().getSelectedItem();
        if (selectedFuite != null) {
            TextInputDialog dialog = new TextInputDialog(String.valueOf(selectedFuite.getDebit()));
            dialog.setTitle("Modifier Fuite");
            dialog.setHeaderText("Modifier le débit de la fuite sélectionnée");
            dialog.setContentText("Débit (L/s):");

            dialog.showAndWait().ifPresent(value -> {
                selectedFuite.setDebit(Integer.parseInt(value));
                tableFuites.refresh();
            });
        }
    }

    /**
     * Supprime la fuite sélectionnée de la simulation.
     */
    @FXML
    private void supprimerFuite() {
        Fuite selectedFuite = tableFuites.getSelectionModel().getSelectedItem();
        if (selectedFuite != null) {
//            selectedFuite.arreter();
            selectedFuite.setPass(true);
            fuitesObservable.remove(selectedFuite);
//            ControleurPrincipal.getSimulation().getFuites().remove(selectedFuite);
        }
    }

    /**
     * Modifie la capacité de la baignoire.
     */
    @FXML
    private void modifierCapaciteBaignoire() {
        int nouvelleCapacite = Integer.parseInt(capaciteBaignoire.getText());
        ControleurPrincipal.getSimulation().getBaignoire().setCapacite(nouvelleCapacite);
    }
}
