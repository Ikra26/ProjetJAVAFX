package fr.ul.miage.ikram.projet.utilitaires;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Classe utilitaire pour exporter les données de simulation vers un fichier CSV.
 */
public class ExportateurCsv {
    /**
     * Exporte les données fournies vers un fichier CSV.
     *
     * @param data         la liste des données à exporter, chaque entrée est un tableau de chaînes de caractères
     * @param cheminFichier le chemin du fichier CSV où les données seront exportées
     */
    public static void exporter(List<String[]> data, String cheminFichier) {
        try (FileWriter writer = new FileWriter(cheminFichier)) {
            // Écrit l'en-tête du fichier CSV
            writer.write("heure,niveauEau\n");

            // Pour chaque ligne de données
            for (String[] ligne : data) {
                // Écrit les éléments de la ligne, séparés par des virgules
                writer.write(String.join(",", ligne));
                // Ajoute un saut de ligne après chaque ligne de données
                writer.write("\n");
            }
        } catch (IOException e) {
            // Affiche l'exception en cas d'erreur d'écriture dans le fichier
            e.printStackTrace();
        }
    }
}
