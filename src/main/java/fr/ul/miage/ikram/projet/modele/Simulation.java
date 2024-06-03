package fr.ul.miage.ikram.projet.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

/**
 * Gère la simulation de remplissage et de fuite de la baignoire.
 */
public class Simulation {
    /**
     * La baignoire utilisée dans la simulation.
     */
    private Baignoire baignoire;

    /**
     * La liste des robinets dans la simulation.
     */
    private List<Robinet> robinets;

    /**
     * La liste des fuites dans la simulation.
     */
    private List<Fuite> fuites;

    /**
     * L'heure actuelle de la simulation.
     */
    private static int heure;

    /**
     * La liste des sémaphores utilisés pour synchroniser les threads.
     */
    private List<Semaphore> semaphores;

    /**
     * L'index des threads pour la synchronisation.
     */
    public static int threadIndex = 0;

    /**
     * Indique si la simulation est en pause.
     */
    private boolean isPaused = false;

    /**
     * Thread pour la tâche de contrôle.
     */
    private Thread controllerTaskThread;

    /**
     * Liste pour stocker les données de la simulation.
     */
    private static List<int[]> data;

    /**
     * Constructeur de la classe Simulation.
     *
     * @param baignoire la baignoire utilisée dans la simulation
     * @param robinets  la liste des robinets
     * @param fuites    la liste des fuites
     * Initialise les attributs de la simulation avec les valeurs fournies et initialise l'heure à 0.
     */
    public Simulation(Baignoire baignoire, List<Robinet> robinets, List<Fuite> fuites) {
        this.baignoire = baignoire;
        this.robinets = robinets;
        this.fuites = fuites;
        this.heure = 0;
        this.data = new ArrayList<>();
    }

    /**
     * Constructeur par défaut de la classe Simulation.
     * Initialise la baignoire avec une capacité de 1000 litres et crée des listes vides pour les robinets et les fuites.
     */
    public Simulation() {
        this(new Baignoire(1000), new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Démarre la simulation.
     * Initialise les sémaphores et démarre les threads pour les robinets et les fuites.
     */
    public synchronized void demarrer() {
        if (isPaused) {
            resume();
            return;
        }
        this.semaphores = new ArrayList<>();
        for (int i = 0; i < robinets.size() + fuites.size() + 2; i++) {
            semaphores.add(new Semaphore(0));
        }
        semaphores.get(0).release();
        for (Robinet robinet : robinets) {
            robinet.setSemaphores(semaphores);
            robinet.setThreadIndex(threadIndex++);
            robinet.start();
        }
        for (Fuite fuite : fuites) {
            fuite.setSemaphores(semaphores);
            fuite.setThreadIndex(threadIndex++);
            fuite.start();
        }
        controllerTaskThread = new Thread(this::controllerTask);
        controllerTaskThread.start();
    }

    /**
     * Arrête la simulation en douceur.
     * Arrête les robinets et répare les fuites, puis arrête la tâche de contrôle.
     */
    public synchronized void arreter() {
        for (Robinet robinet : robinets) {
            robinet.arreter();
        }
        for (Fuite fuite : fuites) {
            fuite.reparer();
        }
        stopControllerTask();
    }

    /**
     * Arrête la simulation de manière forcée.
     * Arrête les robinets et les fuites, puis arrête la tâche de contrôle.
     */
    public synchronized void arreterForcement() {
        for (Robinet robinet : robinets) {
            robinet.arreter();
        }
        for (Fuite fuite : fuites) {
            fuite.arreter();
        }
        stopControllerTask();
    }

    /**
     * Pause la simulation.
     * Met en pause tous les robinets et fuites en cours.
     */
    public synchronized void pause() {
        isPaused = true;
        for (Robinet robinet : robinets) {
            robinet.pause();
        }
        for (Fuite fuite : fuites) {
            fuite.pause();
        }
    }

    /**
     * Reprend la simulation après une pause.
     * Redémarre tous les robinets et fuites en cours.
     */
    public synchronized void resume() {
        isPaused = false;
        for (Robinet robinet : robinets) {
            robinet.resumeThread();
        }
        for (Fuite fuite : fuites) {
            fuite.resumeThread();
        }
    }

    /**
     * Réinitialise la simulation à ses valeurs initiales.
     * Réinitialise la baignoire, les robinets, les fuites et l'heure de la simulation.
     */
    public synchronized void reset() {
        arreterForcement();
        this.baignoire.reset();
        for (int i = 0; i < robinets.size(); i++) {
            robinets.set(i, new Robinet(robinets.get(i)));
        }
        for (int i = 0; i < fuites.size(); i++) {
            fuites.set(i, new Fuite(fuites.get(i)));
        }
        removeAllPassFuites();
        this.isPaused = false;
        this.heure = 0;
        threadIndex = 0;
        this.data.clear();
    }

    public void removeAllPassFuites(){
        this.fuites.removeIf(fuite -> fuite.isPass());
    }

    /**
     * Obtient la baignoire utilisée dans la simulation.
     *
     * @return la baignoire
     * Retourne l'objet baignoire de la simulation.
     */
    public Baignoire getBaignoire() {
        return baignoire;
    }

    /**
     * Obtient la liste des robinets dans la simulation.
     *
     * @return la liste des robinets
     * Retourne la liste des robinets.
     */
    public List<Robinet> getRobinets() {
        return robinets;
    }

    /**
     * Obtient la liste des fuites dans la simulation.
     *
     * @return la liste des fuites
     * Retourne la liste des fuites.
     */
    public List<Fuite> getFuites() {
        return fuites;
    }

    /**
     * Obtient la liste des fuites dans la simulation qui ne sont pas en mode pass
     *
     * @return la liste des fuites
     * Retourne la liste des fuites.
     */
    public List<Fuite> getNonPassFuites() {
        return fuites.stream().filter(fuite -> !fuite.isPass()).collect(Collectors.toList());
    }

    /**
     * Obtient l'heure actuelle de la simulation.
     *
     * @return l'heure actuelle
     * Retourne l'heure actuelle de la simulation.
     */
    public static synchronized int getHeure() {
        return heure;
    }

    /**
     * Incrémente l'heure de la simulation.
     * Augmente l'heure de la simulation de 1.
     */
    public synchronized void incrementerHeure() {
        this.heure++;
    }

    /**
     * Incrémente l'heure de la simulation et ajoute les données de l'heure et du niveau d'eau à la liste.
     * Ajoute une entrée à la liste des données avec l'heure et le niveau d'eau actuel.
     */
    public synchronized void addDataToList() {
        data.add(new int[]{heure, baignoire.getNiveauEau()});
    }

    /**
     * Ajoute un robinet à la simulation.
     *
     * @param r le robinet à ajouter
     * Ajoute un robinet à la liste des robinets.
     */
    public void addRobinet(Robinet r) {
        this.robinets.add(r);
    }

    /**
     * Ajoute une fuite à la simulation.
     *
     * @param f la fuite à ajouter
     * Ajoute une fuite à la liste des fuites.
     */
    public void addFuite(Fuite f) {
        this.fuites.add(f);
    }

    /**
     * Tâche de contrôle pour gérer la synchronisation des threads.
     * Acquiert et libère les sémaphores pour synchroniser les threads de la simulation.
     */
    private void controllerTask() {
        int index = threadIndex++;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // Acquiert le sémaphore pour ce thread
                semaphores.get(index).acquire();
                // Attente active tant que le thread est en pause
                while (isPaused) {
                    Thread.sleep(100); // Petit délai pour éviter une attente active trop agressive
                }
                // Logique du contrôleur
                incrementerHeure();
                addDataToList();
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

    /**
     * Méthode pour arrêter la tâche de contrôle.
     * Interrompt le thread de la tâche de contrôle et attend sa terminaison.
     */
    private void stopControllerTask() {
        if (controllerTaskThread != null) {
            controllerTaskThread.interrupt();
            try {
                controllerTaskThread.join(); // Assure que le thread est arrêté
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Obtient la liste des sémaphores.
     *
     * @return la liste des sémaphores
     * Retourne la liste des sémaphores.
     */
    public List<Semaphore> getSemaphores() {
        return semaphores;
    }

    /**
     * Obtient la liste des données de la simulation.
     *
     * @return la liste des données de la simulation
     * Retourne la liste des données de la simulation.
     */
    public static List<int[]> getData() {
        return data;
    }
}
