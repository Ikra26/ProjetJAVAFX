package fr.ul.miage.ikram.projet.modele;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Représente une fuite avec un débit de fuite.
 */
public class Fuite extends Thread {
    /**
     * Compteur statique pour générer des identifiants uniques pour chaque fuite.
     * Chaque fuite aura un identifiant unique.
     */
    private static int num = 0;

    /**
     * Identifiant unique de la fuite.
     */
    private int idFuite;

    /**
     * Débit de la fuite en litres par seconde.
     * Représente la quantité d'eau qui fuit par seconde.
     */
    private int debit;

    /**
     * La baignoire associée à la fuite.
     * La baignoire d'où l'eau fuit.
     */
    private Baignoire baignoire;

    /**
     * Indique si la fuite est active.
     * Si la fuite est active, cela vaut true; sinon, false.
     */
    private boolean actif;

    /**
     * Indique si le thread de la fuite est en pause.
     * Si le thread est en pause, cela vaut true; sinon, false.
     */
    private boolean isPaused;

    /**
     * Indique si le thread doit simplement passer sans faire d'action.
     */
    private boolean pass;

    /**
     * Liste des sémaphores utilisés pour synchroniser les threads.
     * Utilisé pour contrôler l'ordre d'exécution des threads.
     */
    private List<Semaphore> semaphores;

    /**
     * L'index du thread dans la liste des sémaphores.
     * Utilisé pour identifier le sémaphore correspondant à ce thread.
     */
    private int threadIndex;

    /**
     * Constructeur pour créer une fuite avec un débit spécifié et une baignoire associée.
     *
     * @param debit     le débit de la fuite en litres par seconde
     * @param baignoire la baignoire associée à la fuite
     * Initialise l'identifiant de la fuite, le débit, la baignoire, et met actif à true et isPaused à false.
     */
    public Fuite(int debit, Baignoire baignoire) {
        this.idFuite = ++num;
        this.debit = debit;
        this.baignoire = baignoire;
        this.actif = true;
        this.isPaused = false;
        this.pass = false;
    }

    /**
     * Constructeur pour créer une copie d'une fuite existante.
     *
     * @param f l'objet Fuite à copier
     * Copie les propriétés de l'objet fuite passé en paramètre.
     */
    public Fuite(Fuite f) {
        this.idFuite = f.idFuite;
        this.debit = f.debit;
        this.baignoire = f.baignoire;
        this.pass = f.pass;
        this.actif = true;
        this.isPaused = false;
        this.semaphores = f.semaphores;
        this.threadIndex = f.threadIndex;
    }

    /**
     * Définit le débit de la fuite.
     *
     * @param debit le nouveau débit en litres par seconde
     * Change la valeur du débit de la fuite.
     */
    public void setDebit(int debit) {
        this.debit = debit;
    }

    /**
     * Définit la liste des sémaphores utilisés pour synchroniser les threads.
     *
     * @param semaphores la liste des sémaphores
     * Associe la liste des sémaphores à la fuite.
     */
    public void setSemaphores(List<Semaphore> semaphores) {
        this.semaphores = semaphores;
    }

    /**
     * Définit l'index du thread dans la liste des sémaphores.
     *
     * @param threadIndex l'index du thread
     * Change l'index de ce thread dans la liste des sémaphores.
     */
    public void setThreadIndex(int threadIndex) {
        this.threadIndex = threadIndex;
    }

    /**
     * Obtient le débit de la fuite.
     *
     * @return le débit en litres par seconde
     * Retourne la quantité d'eau qui fuit par seconde.
     */
    public int getDebit() {
        return debit;
    }

    /**
     * Définit si le thread doit simplement passer sans faire d'action.
     *
     * @param pass true si le thread doit passer, sinon false
     */
    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public boolean isPass() {
        return pass;
    }

    /**
     * Obtient l'identifiant unique de la fuite.
     *
     * @return l'identifiant de la fuite
     * Retourne l'identifiant unique de la fuite.
     */
    public int getIdFuite() {
        return idFuite;
    }

    /**
     * Répare la fuite, ce qui la désactive.
     * Met actif à false pour indiquer que la fuite est réparée.
     */
    public void reparer() {
        this.actif = false;
    }

    /**
     * Arrête la fuite et interrompt le thread.
     * Met actif à false et interrompt le thread.
     */
    public void arreter() {
        this.actif = false;
        this.interrupt();
    }

    /**
     * Met en pause l'exécution du thread de la fuite.
     * Change l'état de isPaused à true.
     */
    public void pause() {
        this.isPaused = true;
    }

    /**
     * Reprend l'exécution du thread de la fuite.
     * Change l'état de isPaused à false.
     */
    public void resumeThread() {
        this.isPaused = false;
    }

    /**
     * La méthode d'exécution du thread qui gère la fuite.
     */
    @Override
    public void run() {
        while (actif) {
            try {
                // Acquiert le sémaphore pour ce thread
                semaphores.get(threadIndex).acquire();
                // Attente active tant que le thread est en pause
                while (isPaused) {
                    Thread.sleep(100); // Petit délai pour éviter une attente active trop agressive
                }
                // Si la fuite est active et pass est false, retire de l'eau de la baignoire
                if (actif && !pass) {
                    baignoire.retirerEau(debit);
                    System.out.println(Simulation.getHeure() + "- Fuite " + this.idFuite + " removed " + this.debit);
                }
                // Libère le sémaphore pour le prochain thread
                semaphores.get((threadIndex + 1) % semaphores.size()).release();
                // Attend une seconde avant la prochaine itération
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Interrompt le thread en cas d'exception
                Thread.currentThread().interrupt();
            }
        }
    }
}
