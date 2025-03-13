package cursed_chronicles.Combat;

import cursed_chronicles.Map.Room;
import cursed_chronicles.Monster.Monster;
import cursed_chronicles.Player.Player;
import cursed_chronicles.Player.Characteristic;

import java.util.ArrayList;

public class CombatManager {
    private Player _player;
    private ArrayList<Monster> _monsters; 
    private boolean _playerAttacking; // ✅ Indique si le joueur attaque (espace)
    private Room _currentRoom;

    public CombatManager(Player player) {
        this._player = player;
        this._monsters = new ArrayList<>();
        this._playerAttacking = false;
    }

    public void updateCombat() {
        if (_monsters.isEmpty()) return; // ✅ Évite une boucle inutile

        for (Monster monster : new ArrayList<>(_monsters)) { // ✅ Copie pour éviter les modifications en cours d'itération
            engageCombat(monster);
        }
    }

    public void setPlayer(Player player) {
        this._player = player;
    }

    public void setPlayerAttacking(boolean attacking) {
        this._playerAttacking = attacking;
    }

    private boolean isAdjacent(Player player, Monster monster) {
        int px = player.getPositionX();
        int py = player.getPositionY();
        int mx = monster.getPositionX();
        int my = monster.getPositionY();

        // ✅ Vérifie si le monstre est adjacent au joueur (mais pas sur lui)
        return (Math.abs(px - mx) == 1 && py == my) || (Math.abs(py - my) == 1 && px == mx);
    }

    private boolean isOnSamePosition(Player player, Monster monster) {
        return player.getPositionX() == monster.getPositionX() && player.getPositionY() == monster.getPositionY();
    }

    private void engageCombat(Monster monster) {
        if (monster == null) return; 

        Characteristic playerLife = _player.getCharacteristic("life");
        Characteristic monsterLife = monster.getCharacteristic("life");
        Characteristic playerAttack = _player.getCharacteristic("damage");
        Characteristic monsterAttack = monster.getCharacteristic("damage");

        if (playerLife == null || monsterLife == null || playerAttack == null || monsterAttack == null) {
            System.out.println("⚠ Erreur : Une caractéristique est null !");
            return; 
        }

        int monsterDamage = monsterAttack.getValue();
        int playerDamage = playerAttack.getValue();

        System.out.println("🔍 Combat entre Joueur et " + monster.getName());
        System.out.println("🛡 Joueur PV avant : " + playerLife.getValue());
        System.out.println("🛡 Monstre PV avant : " + monsterLife.getValue());

        // ✅ Le joueur attaque s'il a appuyé sur espace et si le monstre est adjacent
        if (_playerAttacking && isAdjacent(_player, monster)) {
            monsterLife.setValue(Math.max(0, monsterLife.getValue() - playerDamage)); 
            System.out.println("⚔ Le joueur attaque ! Monstre perd " + playerDamage + " PV.");
        }

        // ✅ Le monstre attaque SEULEMENT s'il est SUR la position du joueur
        if (isOnSamePosition(_player, monster)) {
            _player.modifyCharacteristic("life", -monsterDamage);
            System.out.println("⚔ Le monstre attaque ! Joueur perd " + monsterDamage + " PV.");
        }

        System.out.println("🛡 Joueur PV après : " + _player.getCharacteristic("life").getValue());
        System.out.println("🛡 Monstre PV après : " + monsterLife.getValue());

        if (monsterLife.getValue() <= 0) {
            removeMonsterFromRoom(monster);
            System.out.println("💀 " + monster.getName() + " a été vaincu !");
        }

        if (_player.getCharacteristic("life").getValue() <= 0) {
            System.out.println("☠ Game Over... Vous avez été vaincu !");
        }

        _playerAttacking = false; // ✅ Réinitialise après combat
    }

    private void removeMonsterFromRoom(Monster monster) {
        if (_currentRoom != null && _currentRoom.getMonsters().contains(monster)) {
            _currentRoom.removeMonster(monster);
            _monsters.remove(monster);
        }
    }
    
    public void setMonsters(ArrayList<Monster> monsters) {
        this._monsters = (monsters != null) ? monsters : new ArrayList<>();
    }
    
    public void setRoom(Room room) {
        this._currentRoom = room;
    }
}