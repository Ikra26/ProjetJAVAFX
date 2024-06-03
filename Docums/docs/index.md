# Simulation de Remplissage de Baignoire

La simulation de remplissage de baignoire est un programme qui permet de simuler le remplissage d'une baignoire et de gérer les paramètres tels que le débit du robinet et la présence de fuites. Le programme est écrit en Java et utilise JavaFX pour l'interface graphique.

## Architecture

Le programme est constitué de plusieurs classes qui sont responsables de différentes tâches. La classe principale est la classe `Simulation` qui est responsable de la simulation du remplissage de la baignoire. Elle utilise des méthodes pour gérer les débits des robinets et les fuites ainsi que le niveau d'eau dans la baignoire. La classe `ControleurPrincipal` est responsable de l'interface graphique et permet à l'utilisateur de lancer, mettre en pause, arrêter et réinitialiser la simulation. La classe `App` est responsable de la création de la scène et du chargement de la vue.

## Explications des classes

Explications détaillées des classes et de leurs membres :

### Classe `App`

La classe `App` est le point d'entrée de l'application. Elle contient la méthode `main` qui lance l'application JavaFX.

### Classe `Launcher`

La classe `Launcher` est responsable du lancement de l'application. Elle est utilisée pour contourner un problème avec le lancement direct d'applications JavaFX.

### Classe `Simulation`

La classe `Simulation` représente la simulation du remplissage de la baignoire. Elle contient les attributs privés suivants :

- `baignoire`: un objet représentant la baignoire.
- `robinet`: un objet représentant le débit du robinet.
- `fuites`: une liste d'objets représentant les fuites dans la baignoire.
- `niveauEau`: le niveau actuel de l'eau dans la baignoire.
- `volumeInitial`: le volume initial d'eau dans la baignoire.

Les méthodes publiques de la classe `Simulation` comprennent :

- Le constructeur `Simulation` qui initialise les attributs de la simulation.
- `lancerSimulation()`: démarre la simulation du remplissage de la baignoire.
- `mettreEnPause()`: met en pause la simulation.
- `arreterSimulation()`: arrête la simulation.
- `reinitialiserSimulation()`: réinitialise les paramètres de la simulation.
- `calculerDebitTotal()`: calcule le débit total en prenant en compte le débit du robinet et les fuites.
- `verifierDisponibiliteEau()`: vérifie si l'eau est disponible pour remplir la baignoire.

### Classe `ControleurPrincipal`

La classe `ControleurPrincipal` est responsable de l'interface graphique et de la gestion des interactions avec la simulation. Elle contient un attribut privé `simulation` de type `Simulation`.

Les méthodes publiques de la classe `ControleurPrincipal` comprennent :

- Le constructeur `ControleurPrincipal` qui reçoit une instance de `Simulation` pour l'associer à l'interface graphique.
- `lancerSimulation()`: démarre la simulation de la baignoire en appelant la méthode correspondante dans l'objet `simulation`.
- `mettreEnPause()`: met en pause la simulation en appelant la méthode correspondante dans l'objet `simulation`.
- `arreterSimulation()`: arrête la simulation en appelant la méthode correspondante dans l'objet `simulation`.
- `reinitialiserSimulation()`: réinitialise la simulation en appelant la méthode correspondante dans l'objet `simulation`.

### Classe `ParametresControleur`

La classe `ParametresControleur` est responsable de la gestion des paramètres de la simulation. Elle permet à l'utilisateur de configurer les paramètres de la simulation avant de la lancer. 

Les méthodes publiques de la classe `ParametresControleur` comprennent :

- Le constructeur `ParametresControleur` qui initialise les paramètres de la simulation.
- `mettreAJourParametres()`: met à jour les paramètres de la simulation en fonction des entrées de l'utilisateur.
- `getParametres()`: retourne les paramètres actuels de la simulation.

### Classe `GraphiqueControleur`

La classe `GraphiqueControleur` est responsable de l'affichage graphique des résultats de la simulation. Elle permet de visualiser l'évolution du niveau d'eau dans la baignoire au fil du temps.

Les méthodes publiques de la classe `GraphiqueControleur` comprennent :

- Le constructeur `GraphiqueControleur` qui initialise le graphique.
- `mettreAJourGraphique()`: met à jour le graphique en fonction des nouvelles données de la simulation.

### Classe `Baignoire`

La classe `Baignoire` représente la baignoire elle-même. Elle contient les attributs suivants :

- `volume`: le volume total de la baignoire.
- `niveauEau`: le niveau actuel de l'eau dans la baignoire.

Les méthodes de la classe `Baignoire` comprennent :

- Le constructeur `Baignoire` qui initialise le volume et le niveau d'eau de la baignoire.
- `ajouterEau(volume)`: ajoute une certaine quantité d'eau à la baignoire.
- `retirerEau(volume)`: retire une certaine quantité d'eau de la baignoire.
- `getNiveauEau()`: retourne le niveau actuel de l'eau dans la baignoire.

### Classe `Robinet`

La classe `Robinet` représente le robinet de la baignoire. Elle contient les attributs suivants :

- `debit`: le débit d'eau du robinet.

Les méthodes de la classe `Robinet` comprennent :

- Le constructeur `Robinet` qui initialise le débit d'eau.
- `ouvrir()`: ouvre le robinet et commence à ajouter de l'eau à la baignoire.
- `fermer()`: ferme le robinet et arrête d'ajouter de l'eau à la baignoire.
- `getDebit()`: retourne le débit actuel du robinet.

### Classe `Fuite`

La classe `Fuite` représente une fuite dans la baignoire. Elle contient les attributs suivants :

- `debit`: le débit de la fuite.

Les méthodes de la classe `Fuite` comprennent :

- Le constructeur `Fuite` qui initialise le débit de la fuite.
- `getDebit()`: retourne le débit actuel de la fuite.

### Classe `ConvertisseurTemps`

La classe `ConvertisseurTemps` contient des méthodes utilitaires pour convertir des unités de temps. Les méthodes de la classe `ConvertisseurTemps` comprennent :

- `convertirHeuresEnMinutes(heures)`: convertit des heures en minutes.
- `convertirMinutesEnSecondes(minutes)`: convertit des minutes en secondes.

### Classe `ExportateurCsv`

La classe `ExportateurCsv` contient des méthodes utilitaires pour exporter des données de simulation au format CSV. Les méthodes de la classe `ExportateurCsv` comprennent :

- `exporterDonneesSimulation(simulation, fichier)`: exporte les données de la simulation vers un fichier CSV.


