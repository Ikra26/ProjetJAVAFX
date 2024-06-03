package fr.ul.miage.ikram.projet.modele;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Représente un robinet avec un débit.
 */
public class Robinet extends Thread {
    /**
     * Compteur statique pour générer des identifiants uniques pour chaque robinet.
     * Chaque robinet aura un identifiant unique.
     */
    private static int num = 0;

    /**
     * Identifiant unique du robinet.
     */
    private int idRobinet;

    /**
     * Débit du robinet en litres par seconde.
     * Représente la quantité d'eau que le robinet ajoute par seconde.
     */
    private int debit;

    /**
     * La baignoire associée au robinet.
     * La baignoire dans laquelle l'eau est ajoutée.
     */
    private Baignoire baignoire;

    /**
     * Indique si le robinet est actif.
     * Si le robinet est actif, cela vaut true; sinon, false.
     */
    private boolean actif;

    /**
     * Indique si le thread du robinet est en pause.
     * Si le thread est en pause, cela vaut true; sinon, false.
     */
    private boolean isPaused;

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
     * Constructeur pour créer un robinet avec un débit spécifié et une baignoire associée.
     *
     * @param debit     le débit du robinet en litres par seconde
     * @param baignoire la baignoire associée au robinet
     * Initialise l'identifiant du robinet, le débit, la baignoire, et met actif à true et isPaused à false.
     */
    public Robinet(int debit, Baignoire baignoire) {
        this.idRobinet = ++num;
        this.debit = debit;
        this.baignoire = baignoire;
        this.actif = true;
        this.isPaused = false;
    }

    /**
     * Constructeur pour créer une copie d'un robinet existant.
     *
     * @param r l'objet Robinet à copier
     * Copie les propriétés de l'objet robinet passé en paramètre.
     */
    public Robinet(Robinet r) {
        this.idRobinet = r.idRobinet;
        this.debit = r.debit;
        this.baignoire = r.baignoire;
        this.actif = true;
        this.isPaused = false;
        this.semaphores = r.semaphores;
        this.threadIndex = r.threadIndex;
    }

    /**
     * Obtient l'identifiant unique du robinet.
     *
     * @return l'identifiant du robinet
     * Retourne l'identifiant unique du robinet.
     */
    public int getIdRobinet() {
        return idRobinet;
    }

    /**
     * Définit l'identifiant unique du robinet.
     *
     * @param idRobinet le nouvel identifiant du robinet
     * Change la valeur de l'identifiant du robinet.
     */
    public void setIdRobinet(int idRobinet) {
        this.idRobinet = idRobinet;
    }

    /**
     * Définit le débit du robinet.
     *
     * @param debit le nouveau débit en litres par seconde
     * Change la valeur du débit du robinet.
     */
    public void setDebit(int debit) {
        this.debit = debit;
    }

    /**
     * Définit la liste des sémaphores utilisés pour synchroniser les threads.
     *
     * @param semaphores la liste des sémaphores
     * Associe la liste des sémaphores au robinet.
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
     * Obtient le débit du robinet.
     *
     * @return le débit en litres par seconde
     * Retourne la quantité d'eau que le robinet ajoute par seconde.
     */
    public int getDebit() {
        return debit;
    }

    /**
     * Arrête le robinet et interrompt le thread.
     * Met actif à false et interrompt le thread.
     */
    public void arreter() {
        this.actif = false;
        this.interrupt();
    }

    /**
     * Met en pause l'exécution du thread du robinet.
     * Change l'état de isPaused à true.
     */
    public void pause() {
        this.isPaused = true;
    }

    /**
     * Reprend l'exécution du thread du robinet.
     * Change l'état de isPaused à false.
     */
    public void resumeThread() {
        this.isPaused = false;
    }

    /**
     * La méthode d'exécution du thread qui gère le robinet.
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
                // Si le robinet est actif, ajoute de l'eau à la baignoire
                if (actif) {
                    baignoire.ajouterEau(debit);
                    System.out.println(Simulation.getHeure() + "- Robinet " + this.idRobinet + " sent " + this.debit);
                    // Arrête le robinet si la baignoire est pleine
                    if (baignoire.estPleine()) {
                        arreter();
                    }
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
