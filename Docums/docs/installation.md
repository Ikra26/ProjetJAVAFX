# Installation

## Prérequis

Avant de commencer, assurez-vous d'avoir installé les éléments suivants sur votre système :

- [Java Development Kit (JDK) 11 ou plus récent](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)
- Un IDE tel qu'[Eclipse](https://www.eclipse.org/downloads/) ou [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)

## Installation source

### 1. Cloner le dépôt

Clonez le dépôt de votre projet en utilisant la commande suivante dans votre terminal :

```sh
git clone https://github.com/votre-utilisateur/votre-repo.git
```
Naviguer dans le répertoire du projet
Déplacez-vous dans le répertoire du projet cloné :

```sh
cd votre-repo
```
Remplacez votre-repo par le nom du répertoire du projet.

3. Construire le projet avec Maven
Utilisez Maven pour construire le projet. Cette commande téléchargera les dépendances nécessaires et compilera le projet :

```sh
mvn clean install
```
4. Exécuter le projet
Une fois le projet construit, vous pouvez l'exécuter en utilisant la commande suivante :

```sh
mvn javafx:run
```
Cela lancera l'application JavaFX.

##Installation à partir d'une distribution binaire
1. Télécharger l'archive

2. Extraire l'archive
Extrayez le contenu de l'archive téléchargée :

```sh
unzip votre-projet-distribution-binaire.zip
```
3. Naviguer dans le répertoire extrait
Déplacez-vous dans le répertoire extrait :

```sh
cd votre-projet-distribution-binaire
```
Remplacez votre-projet-distribution-binaire par le nom du répertoire extrait.

4. Exécuter le projet
Utilisez le script fourni pour exécuter le projet.  :

```sh
./run.sh
```
Si vous êtes sous Windows, exécutez le script .bat fourni :

```sh
run.bat
```
## Exécution via un IDE après installation
Après avoir télécharger le projet :
#### IntelliJ IDEA
Ouvrez votre projet dans IntelliJ IDEA.
Allez dans Run > Edit Configurations.
Cliquez sur le bouton "+" et sélectionnez "Application".
Cliquez sur "Run" (flèche verte) ou appuyez sur Shift+F10.
#### Eclipse
Ouvrez votre projet dans Eclipse.
Faites un clic droit sur votre projet dans l'Explorateur de projet.
Sélectionnez la classe launcher.
Cliquez sur "Run" (flèche verte) ou appuyez sur Ctrl+F11.

##Problème d'éxecution avec le terminal, veuillez éxecuter le projet par un IDE