# 🏰 Cursed Chronicles

**Cursed Chronicles** est un **jeu d’exploration et de combat** en 2D se déroulant dans un **monde maudit** composé de plusieurs donjons.  
L'objectif ? **Dévoiler une histoire oubliée en rassemblant des indices cachés** à travers des combats et des énigmes.  

---

## 🌍 **Présentation du Monde**

Le jeu se compose de **3 donjons** et d’une **salle d’accueil** :  

- 🏠 **Salle d’accueil** : Le point de départ du joueur.  
- 🏰 **Donjon 1** : Lieu mystérieux où débute l’aventure.  
- ⚰️ **Donjon 2** : Un endroit plus dangereux, rempli d’ennemis plus puissants.  
- 🔥 **Donjon 3** : Le plus difficile, gardant les secrets les plus précieux.  

Dans chaque donjon, le joueur doit **explorer, combattre et récupérer des indices** pour reconstituer une histoire perdue dans l’oubli.  

---

## 🎮 **Gameplay & Mécaniques de Jeu**  

### 🗺 **Exploration**
- Tu **te déplaces** dans un monde en tuiles, salle par salle.  
- Chaque salle contient **des monstres**, **des pièges**, **des coffres**, et parfois **des indices**.  
- L’exploration se fait via une narration dynamique affichée à chaque changement de salle.  

### ⚔️ **Système de Combat**
- Les **monstres** peuvent **se déplacer et attaquer** le joueur.  
- Si un monstre est **adjacent**, tu peux **l’attaquer avec `Espace`**.  
- Chaque **monstre de niveau 2** donne un **indice** à sa défaite. *(Voir raccourci pour tester plus vite)*.  

### 🎁 **Ramassage d’objets**
- 🏆 **Boosters** : Améliorent les stats du joueur (`Vie`, `Défense`, `Vitesse`).  
- ⚔️ **Armes** : Changent les dégâts infligés en combat.  
- 📜 **Indices** : Récupérés sur les **monstres de niveau 2** (modifiable, voir plus bas).  

---

## 🔑 **Objectif : Déverrouiller l’Histoire Secrète**
1. 🏹 **Combats les monstres de niveau 2** pour **récupérer des indices**.  
2. 📖 **Les indices sont stockés dans le journal** (`J` pour ouvrir).  
3. 🧩 **Une fois tous les indices trouvés**, une **boîte de dialogue** apparaît et te demande de **deviner le mot de passe final**.  
4. 🏆 **Si tu réussis, l’histoire complète est révélée !**  

📂 **Les réponses aux énigmes sont cachées ici** :  
- **Fichier :** `resources/histoire-cursed-chronicles.txt`  
- **Logs :** L'histoire sélectionnée s'affiche dans la console au lancement du jeu.  

---

## 🏹 **Commandes & Contrôles**  

| Action               | Touche          |
|----------------------|----------------|
| 🏃 **Déplacement**   | Flèches ⬆️⬇️⬅️➡️ |
| ⚔️ **Attaquer**      | `Espace`        |
| 🎁 **Ramasser un objet** | `C`         |
| 🏃 **Sprint**        | `S`             |
| 📖 **Ouvrir le journal** | `J`        |
| 🛑 **Quitter**       | `Échap`         |

---

## 🛠 **Installation et Lancement**

### ⚡ **Prérequis :**
- **Java 11+** installé  
- **Eclipse / IntelliJ** (ou tout autre IDE supportant Java)  
- **Git** pour cloner le projet
- **Lancement** le jeu peut etre un peu lourd (plusieurs timer en même temps), si le déplacement du joueur est sacadé, appuyer sur entrer pour passer l'affichage de la narration et mettre sur secteur
- **Utilisation** attention lors d'une partie à bien toujours remettre le focus sur la map sinon le joueur ne répondra plus. Ne pas cliquer dans la zone de narration

### 📥 **Installation :**
1. **Cloner le dépôt** :
   ```sh
   git clone https://github.com/ton-depot/cursed-chronicles.git
