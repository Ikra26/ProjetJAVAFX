# Utilisation

## Vidéo de démonstration

Voici une démonstration vidéo de notre projet :

!!! Si la vidéo ne marche pas video de démo sur cet emplacement  Friend_7-TP_JAVA_Simulation_Remplissage_Baignoire-main\Docums\docs\video

<video width="600" controls>
  <source src="video/demo.mp4" type="video/mp4">
  Your browser does not support the video tag.
</video>


### Fenêtre Principale

Lorsque vous lancez l'application, la fenêtre principale s'ouvre. Voici un aperçu des fonctionnalités disponibles :

Graphique : Affiche le graphique de remplissage de la baignoire en temps réel.
Paramètres : Permet de configurer les paramètres de la simulation, tels que le débit du robinet et les fuites.
Contrôles :
Lancer : Démarre la simulation.
Pause : Met en pause la simulation.
Arrêter : Arrête la simulation.
Réinitialiser : Réinitialise les paramètres et le niveau d'eau de la baignoire.

### Configuration des Paramètres

Pour configurer les paramètres de la simulation :

Ouvrir les paramètres : Cliquez sur le bouton Paramètres.
Modifier les paramètres :
Débit du robinet : Entrez le débit d'eau entrant dans la baignoire en litres par minute.
Ajouter une fuite : Cliquez sur "Ajouter une fuite" et spécifiez le débit de la fuite en litres par minute.
Supprimer une fuite : Sélectionnez une fuite dans la liste et cliquez sur "Supprimer".
Appliquer les paramètres : Cliquez sur modifier capacité si vous avez modifier la capacité de la baignoire pour la sauvegarder sinon les paramètres se sauvegardent directement après avoir fermé la fenêtre.

### Visualisation du Graphique

Le graphique montre l'évolution du niveau d'eau dans la baignoire au fil du temps.


### Fonctionnalités Avancées
#### Exporter les Données
Vous pouvez exporter les données de simulation au format CSV pour une analyse ultérieure. Pour ce faire :

Cliquer sur le bouton exporter CSV.

#### Enregistrer le fichier :
Choisissez l'emplacement et le nom du fichier CSV.
Cliquez sur Enregistrer pour exporter les données.
#### Conversion de Temps
Le programme inclut une fonctionnalité de conversion des unités de temps pour vous aider à ajuster les paramètres :

Heures en Minutes :

```java
int minutes = ConvertisseurTemps.convertirHeuresEnMinutes(heures);
```

Minutes en Secondes :

```java
int secondes = ConvertisseurTemps.convertirMinutesEnSecondes(minutes);
```


