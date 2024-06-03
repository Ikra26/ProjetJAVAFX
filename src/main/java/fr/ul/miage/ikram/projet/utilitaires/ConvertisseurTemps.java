package fr.ul.miage.ikram.projet.utilitaires;

/**
 * Classe utilitaire pour convertir le temps de simulation en format heure:minute:seconde.
 */
public class ConvertisseurTemps {
    /**
     * Convertit un nombre entier représentant les heures en une chaîne de caractères au format hh:mm:ss.
     *
     * @param heure l'heure à convertir
     * @return une chaîne de caractères au format hh:mm:ss
     */
    public static String convertirTemps(int heure) {
        // Formate l'heure en chaîne de caractères avec deux chiffres, et ajoute ":00:00" pour les minutes et secondes
        return String.format("%d:00:00", heure);
    }
}
