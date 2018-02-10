# EmargementNFC

Il s'agit d'une application Android permettant de réaliser un émargement en utilisant les 
cartes étudiants. 

## Contribution

Projet réalisé par Alexandre Pecikoza, Thibault Brentot, Michel Nguyen, Romain Poret, Antoine Duval et Corentin Ballot.

## Fonctionnement

L'activité principale contient deux boutons :

- `Etudiants`
- `Emargement`

Le bouton `Etudiants` permet de voir la liste des étudiants enregistré sur le téléphone, 
et d'un ajouter (via le bouton `Ajouter l'etudiant`). Cliquer sur un étudiant permet de
modifier les données saisie. L'`Identifiant de la carte étudiant` peut être ajouté/modifié
en scannant la carte étudiant.

Le bouton `Emargement` permet de voir la liste des examens et d'en ajouter (via le bouton 
`Nouvel emargement`). Cliquer sur un examen permet de voir les étudiant ayant émargé. Pour
ajouter un étudiant à la liste de cet éargement, il faut simplement scanner sa carte. Le 
bouton `Exporter` permet d'enregistrer les données (dans le répertoire `Download/media`).
