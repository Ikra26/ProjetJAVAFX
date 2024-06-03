package fr.ul.miage.ikram.projet.modele;

/**
 * Représente une baignoire avec une capacité et un niveau d'eau actuel.
 */
public class Baignoire {

    /**
     * La capacité maximale de la baignoire en litres.
     * C'est le maximum d'eau que la baignoire peut contenir.
     */
    private int capacite;

    /**
     * Le niveau actuel d'eau dans la baignoire en litres.
     * Représente la quantité d'eau présente dans la baignoire à un moment donné.
     */
    private int niveauEau;

    /**
     * Indique si la baignoire est pleine ou non.
     * Si la baignoire est pleine, cela vaut true; sinon, false.
     */
    private boolean pleine;

    /**
     * Constructeur pour créer une baignoire avec une capacité spécifiée.
     *
     * @param capacite la capacité maximale de la baignoire en litres
     * Initialise la capacité, le niveau d'eau à 0 et pleine à false.
     */
    public Baignoire(int capacite) {
        this.capacite = capacite;
        this.niveauEau = 0;
        this.pleine = false;
    }

    /**
     * Ajoute une quantité spécifiée d'eau à la baignoire.
     *
     * @param quantite la quantité d'eau à ajouter en litres
     * Si la baignoire n'est pas pleine, ajoute l'eau et vérifie si elle est maintenant pleine.
     */
    public synchronized void ajouterEau(int quantite) {
        if (!pleine) {
            this.niveauEau += quantite;
            if (this.niveauEau >= this.capacite) {
                this.niveauEau = this.capacite;
                this.pleine = true;
            }
        }
    }

    /**
     * Retire une quantité spécifiée d'eau de la baignoire.
     *
     * @param quantite la quantité d'eau à retirer en litres
     * Si la baignoire n'est pas pleine, retire l'eau et vérifie que le niveau d'eau ne descend pas en dessous de 0.
     */
    public synchronized void retirerEau(int quantite) {
        if (!pleine) {
            this.niveauEau -= quantite;
            if (this.niveauEau < 0) {
                this.niveauEau = 0;
            }
        }
    }

    /**
     * Obtient le niveau actuel d'eau dans la baignoire.
     *
     * @return le niveau actuel d'eau en litres
     * Renvoie la quantité d'eau actuellement présente dans la baignoire.
     */
    public synchronized int getNiveauEau() {
        return niveauEau;
    }

    /**
     * Obtient la capacité maximale de la baignoire.
     *
     * @return la capacité de la baignoire en litres
     * Renvoie le maximum d'eau que la baignoire peut contenir.
     */
    public int getCapacite() {
        return capacite;
    }

    /**
     * Définit une nouvelle capacité maximale pour la baignoire.
     *
     * @param capacite la nouvelle capacité en litres
     * Change la capacité maximale de la baignoire.
     */
    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    /**
     * Vérifie si la baignoire est pleine.
     *
     * @return vrai si la baignoire est pleine, sinon faux
     * Indique si la baignoire a atteint sa capacité maximale.
     */
    public synchronized boolean estPleine() {
        return pleine;
    }

    /**
     * Réinitialise le niveau d'eau et l'état de la baignoire.
     * Met le niveau d'eau à 0 et pleine à false.
     */
    public void reset() {
        this.niveauEau = 0;
        this.pleine = false;
    }
}
