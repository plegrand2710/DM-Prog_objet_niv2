# DM-Prog_objet_niv2
# Les Chroniques Déformées

## Univers du jeu

Le jeu se déroule dans le royaume ancien d'**Eldoria**, un monde autrefois prospère mais aujourd’hui rongé par une mystérieuse malédiction qui altère les souvenirs et la réalité. Des légendes oubliées refont surface, et seul un aventurier intrépide peut explorer ces terres pour rassembler les fragments de vérité et reconstruire l’histoire du royaume.

### Lieux emblématiques d'Eldoria
- **Forêts hantées** : Zones envahies par une brume épaisse, où le temps semble figé.
- **Ruines anciennes** : Vestiges d’un empire oublié, recelant des inscriptions et des indices fragmentés.
- **Villages aux habitants amnésiques** : Les villageois parlent en énigmes et ne se souviennent de rien de leur passé.
- **Grottes et cavernes** : Sombres tunnels remplis de créatures oubliées et de trésors légendaires.
- **Temples maudits** : Sanctuaires anciens où reposent les vérités perdues du royaume.

## Concept du jeu

Un jeu de plateau interactif où un joueur explore un monde divisé en tuiles et affronte des monstres. Les indices obtenus après un combat varient en fonction du score obtenu, influençant ainsi l’histoire que le joueur construit au fur et à mesure de son aventure.

### Objectifs du joueur
- Explorer la carte pour collecter des fragments d’histoire.
- Combattre des monstres pour obtenir des indices plus précis.
- Assembler une légende et découvrir la véritable histoire du royaume.
- Éviter les altérations de mémoire causées par la malédiction.

## Mécaniques principales

### 🗺️ 1. Plateau de jeu
- Une grille de tuiles représentant différentes zones du royaume.
- Chaque tuile peut contenir :
  - Un **monstre** à combattre.
  - Un **indice** sur l’histoire.
  - Un **événement spécial** qui affecte le joueur.
- Le joueur peut **se déplacer librement** sur la carte en fonction des tours.

### ⚔️ 2. Système de combat
- **Rencontre avec un monstre** : Lorsqu’un joueur atterrit sur une tuile contenant un monstre, un combat se déclenche.
- **Mécanisme de combat** :
  - Un score entre **1 et 10** est généré aléatoirement.
  - Le score détermine la qualité de l’indice obtenu après la victoire.
- **Récompenses du combat** :
  - **Score faible (1-3)** : Un indice flou et difficile à interpréter.
  - **Score moyen (4-6)** : Un indice partiel mais utile.
  - **Score élevé (7-9)** : Un indice très précis.
  - **Score parfait (10)** : Une révélation clé sur l’histoire.

### 📖 3. Système d’histoire dynamique
- Chaque fragment d’histoire est gagné via un combat.
- Les indices s’accumulent et le joueur doit **interpréter et assembler l’histoire**.
- Certains indices peuvent être contradictoires en fonction des choix du joueur.
- À la fin du jeu, une version complète de l’histoire est affichée, avec **différentes interprétations possibles**.

### 🎭 4. Événements et malédictions
- **Altérations mémorielles** : Certaines tuiles modifient un indice précédent.
- **Rencontres spéciales** : Certains PNJ donnent des indices… mais certains sont trompeurs !
- **Objets et artefacts** : Certains objets permettent d’influencer le jeu (ex : annuler une malédiction, améliorer un indice).

## Technologies et Architecture

### 🖥️ Développement en Java
- **Langage principal** : Java
- **Paradigme** : Programmation orientée objet

### 🏗️ Structure du code
#### 📌 Gestion du plateau
- `Board` : Contient la carte du jeu (matrice de tuiles).
- `Tile` : Représente une case sur le plateau.

#### ⚔️ Système de combat
- `CombatManager` : Gère les combats contre les monstres.
- `Monster` : Définit les caractéristiques des ennemis.

#### 📜 Gestion de l’histoire
- `StoryManager` : Stocke les fragments d’histoire.
- `Clue` : Classe contenant les indices obtenus.

#### 🎮 Joueur et interactions
- `Player` : Contient la position et l’inventaire du joueur.
- `Inventory` : Gère les objets collectés.

#### 🎲 Événements et anomalies
- `EventManager` : Gère les événements spéciaux sur certaines tuiles.
- `MemoryCurse` : Applique les altérations de mémoire.

## Améliorations possibles

Ajout d’un **mode multijoueur** pour une coopération dans la construction de l’histoire.  
Ajout de **dialogues interactifs** avec des PNJ qui influencent l’histoire.  
**Système de quêtes secondaires** permettant d’obtenir des indices alternatifs.  
Ajout d’un **système de points de cohérence** pour mesurer la qualité de l’histoire finale.  
Implémentation de **scénarios dynamiques** où les indices et les événements varient à chaque partie.  

---