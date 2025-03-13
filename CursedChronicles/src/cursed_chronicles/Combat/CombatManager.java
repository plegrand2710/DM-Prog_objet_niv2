package cursed_chronicles.Combat;

import cursed_chronicles.Map.Room;
import cursed_chronicles.Monster.Monster;
import cursed_chronicles.Player.Player;
import cursed_chronicles.Player.Characteristic;

import java.util.ArrayList;
import java.util.Iterator;

public class CombatManager {
    private Player _player;
    private ArrayList<Monster> _monsters;
    private boolean _playerAttacking; 
    private Room _currentRoom;

    public CombatManager(Player player) {
        this._player = player;
        this._monsters = new ArrayList<>();
        this._playerAttacking = false;
    }

    public void updateCombat() {
        if (_monsters.isEmpty()) return; 

        ArrayList<Monster> monstersToRemove = new ArrayList<>();

        System.out.println("🔍 Début du combat contre " + _monsters.size() + " monstre(s)");

        for (Monster monster : _monsters) {
            boolean isMonsterDead = engageCombat(monster);
            if (isMonsterDead) {
                monstersToRemove.add(monster);
            }
        }

        for (Monster monster : monstersToRemove) {
            removeMonsterFromRoom(monster);
            _monsters.remove(monster);
            System.out.println("💀 " + monster.getName() + " a été retiré du combat.");
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

        return (Math.abs(px - mx) == 1 && py == my) || (Math.abs(py - my) == 1 && px == mx);
    }

    private boolean isOnSamePosition(Player player, Monster monster) {
        return player.getPositionX() == monster.getPositionX() && player.getPositionY() == monster.getPositionY();
    }

    private boolean engageCombat(Monster monster) {
        if (monster == null) return false;

        Characteristic playerLife = _player.getCharacteristic("life");
        Characteristic monsterLife = monster.getCharacteristic("life");
        Characteristic playerAttack = _player.getCharacteristic("damage");
        Characteristic monsterAttack = monster.getCharacteristic("damage");

        if (playerLife == null || monsterLife == null || playerAttack == null || monsterAttack == null) {
            System.out.println("⚠ Erreur : Une caractéristique est null !");
            return false;
        }

        int monsterDamage = monsterAttack.getValue();
        int playerDamage = playerAttack.getValue();

        System.out.println("🔍 Combat : Joueur vs " + monster.getName());
        System.out.println("🛡 Joueur PV avant : " + playerLife.getValue());
        System.out.println("🛡 Monstre (" + monster.getName() + ") PV avant : " + monsterLife.getValue());

        boolean monsterDied = false;

        if (_playerAttacking && isAdjacent(_player, monster)) {
            int newMonsterLife = Math.max(0, monsterLife.getValue() - playerDamage);
            monsterLife.setValue(newMonsterLife);

            if (newMonsterLife <= 0) {
                monsterDied = true;
            }
        }

        if (isOnSamePosition(_player, monster)) {
            _player.modifyCharacteristic("life", -monsterDamage);
        }

        System.out.println("🛡 Joueur PV après : " + _player.getCharacteristic("life").getValue());
        System.out.println("🛡 Monstre (" + monster.getName() + ") PV après : " + monsterLife.getValue());

        if (_player.getCharacteristic("life").getValue() <= 0) {
            System.out.println("☠ Game Over... Vous avez été vaincu !");
        }

        return monsterDied;
    }

    private void removeMonsterFromRoom(Monster monster) {
        if (_currentRoom != null && _currentRoom.getMonsters().contains(monster)) {
            _currentRoom.removeMonster(monster);
        }
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this._monsters = (monsters != null) ? new ArrayList<>(monsters) : new ArrayList<>();
    }

    public void setRoom(Room room) {
        this._currentRoom = room;
    }
}