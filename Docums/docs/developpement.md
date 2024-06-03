# Développement



## Classes Principales

- **App.java** : Point d'entrée de l'application.
- **Launcher.java** : Lanceur de l'application JavaFX.
- **ControleurPrincipal.java** : Contrôleur principal pour gérer les interactions utilisateur.
-**Simulation** : Simulation du remplissage de la baignoire

## Documentation de la classe Simulation
La classe Simulation permet de simuler le remplissage de la baignoire. Elle contient la logique de la simulation, incluant la gestion du débit d'entrée, des fuites, et du niveau d'eau dans la baignoire.

### Variables d'instance
baignoire : l'objet Baignoire représentant la baignoire.
robinet : l'objet Robinet représentant le robinet.
fuites : une liste d'objets Fuite représentant les fuites dans la baignoire.
niveauEau : le niveau d'eau actuel dans la baignoire.
volumeInitial : le volume initial d'eau dans la baignoire.
running : un booléen indiquant si la simulation est en cours d'exécution.
paused : un booléen indiquant si la simulation est en pause.
### Méthodes
#### Constructeur
```java
public Simulation(Baignoire baignoire, Robinet robinet, List<Fuite> fuites, double volumeInitial)
```
Le constructeur de la classe Simulation initialise les variables d'instance de la classe. Les paramètres du constructeur sont :

baignoire : l'objet Baignoire représentant la baignoire.
robinet : l'objet Robinet représentant le robinet.
fuites : une liste d'objets Fuite représentant les fuites dans la baignoire.
volumeInitial : le volume initial d'eau dans la baignoire.

#### Méthodes de gestion de la simulation
```java
public void lancerSimulation()
```
Démarre la simulation en réglant le booléen running à true.

```java
public void mettreEnPause()
```
Met en pause la simulation en réglant le booléen paused à true.

```java
public void arreterSimulation()
```
Arrête la simulation en réglant le booléen running à false.

```java
public void reinitialiserSimulation()
```
Réinitialise la simulation en réglant les niveaux d'eau et en réinitialisant les paramètres initiaux.

#### Méthodes de calcul
```java
private double calculerDebitTotal()
```
Calcule le débit total entrant et sortant de la baignoire en tenant compte du débit du robinet et des fuites.

```java
private boolean verifierDisponibiliteEau()
```
Vérifie si la quantité d'eau disponible est suffisante pour continuer la simulation.

## Documentation de la classe ControleurPrincipal
La classe ControleurPrincipal est le contrôleur de la vue principale de la simulation de remplissage de baignoire.

### Variables d'instance
startButton : un bouton pour lancer la simulation.
pauseButton : un bouton pour mettre la simulation en pause ou la reprendre.
stopButton : un bouton pour arrêter la simulation.
resetButton : un bouton pour réinitialiser la simulation.
niveauEauLabel : une étiquette pour afficher le niveau d'eau actuel dans la baignoire.
debitRobinetLabel : une étiquette pour afficher le débit du robinet.
debitFuiteLabel : une étiquette pour afficher le débit des fuites.
### Méthodes
Initialisation
```java
@Override
public void initialize(URL url, ResourceBundle resourceBundle)
```
Initialise la simulation et les éléments de l'interface graphique. Cette méthode est appelée automatiquement lorsque la vue est créée.

#### Méthodes de gestion de l'interface
```java
@FXML
private void startSimulation()
```
Démarre la simulation de remplissage de la baignoire.

```java
@FXML
private void pauseSimulation()
```
Met en pause la simulation ou la reprend selon l'état actuel de la simulation.

```java
@FXML
private void stopSimulation()
```
Arrête la simulation.

```java
@FXML
private void resetSimulation()
```
Réinitialise la simulation aux paramètres initiaux.

#### Méthodes de mise à jour de l'interface
```java
public void setNiveauEau(double niveauEau)
```
Met à jour le niveau d'eau dans l'interface.

niveauEau : le niveau d'eau dans la baignoire.
```java
public void setDebitRobinet(double debitRobinet)
```
Met à jour le débit du robinet dans l'interface.

debitRobinet : le débit du robinet.
```java
public void setDebitFuite(double debitFuite)
```
Met à jour le débit des fuites dans l'interface.

debitFuite : le débit des fuites.

## Documentation de la classe ParametresControleur
La classe ParametresControleur est le contrôleur de la vue de configuration des paramètres de la simulation.

### Variables d'instance
debitRobinetField : un champ de texte pour spécifier le débit du robinet.
ajouterFuiteButton : un bouton pour ajouter une fuite.
supprimerFuiteButton : un bouton pour supprimer une fuite.
fuitesList : une liste pour afficher les fuites actuelles et leurs débits.
appliquerButton : un bouton pour appliquer les paramètres de la simulation.
### Méthodes
Initialisation
```java
@Override
public void initialize(URL url, ResourceBundle resourceBundle)
```
Initialise les éléments de l'interface graphique. Cette méthode est appelée automatiquement lorsque la vue est créée.

#### Méthodes de gestion de l'interface
```java
@FXML
private void ajouterFuite()
```
Ajoute une nouvelle fuite avec le débit spécifié par l'utilisateur.

```java
@FXML
private void supprimerFuite()
```
Supprime la fuite sélectionnée dans la liste des fuites.

```java
@FXML
private void appliquerParametres()
```
Applique les paramètres spécifiés par l'utilisateur à la simulation.

#### Méthodes de mise à jour de l'interface
```java
public void setDebitRobinet(double debitRobinet)
```
Met à jour le débit du robinet dans l'interface.

debitRobinet : le débit du robinet.
### Événements
```java
ajouterFuiteButton.setOnAction(event -> ajouterFuite()) :
```
Lorsque l'utilisateur clique sur le bouton ajouterFuiteButton, une nouvelle fuite est ajoutée avec le débit spécifié.

```java
supprimerFuiteButton.setOnAction(event -> supprimerFuite()) :
```
Lorsque l'utilisateur clique sur le bouton supprimerFuiteButton, la fuite sélectionnée est supprimée.

```java
appliquerButton.setOnAction(event -> appliquerParametres()) :
```
Lorsque l'utilisateur clique sur le bouton appliquerButton, les paramètres sont appliqués à la simulation.

