package cursed_chronicles.Map;

import javax.swing.*;

import cursed_chronicles.Constant;
import cursed_chronicles.Combat.*;
import cursed_chronicles.Monster.*;
import cursed_chronicles.Monster.Monster;
import cursed_chronicles.Monster.RandomMovementStrategy;
import cursed_chronicles.Player.Clue;
import cursed_chronicles.Player.ItemBooster;
import cursed_chronicles.Player.ItemWeapon;
import cursed_chronicles.Player.PlayerController;
import cursed_chronicles.UI.NarrationPanel;
import cursed_chronicles.UI.Story;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class RoomView extends JLayeredPane {
    private static final long serialVersionUID = 1L;
	private JPanel _floorLayer;
    private JPanel _trapsLayer;
    private JPanel _sideWallsLayer;
    private JPanel _sideDoorsLayer;
    private JPanel _frontWallsLayer;
    private JPanel _frontDoorsLayer;
    private JPanel _chestsLayer;
    private JPanel _decorationsLayer;
    private JPanel _pillarLayer;
    private JPanel _boostersLayer;
    private JPanel _monstersLayer;
    private JPanel _weaponsLayer; 
    private Timer monsterMovementTimer; 
    private Timer spaceKeyCheckTimer; 

    private final int _tileSize = 16;
    private final int _scaleFactor = 3;
    private final int _displayTileSize = _tileSize * _scaleFactor;

    private final String _tilesetBasePath = "assets/maps/tiles/";
    private final String _monsterSpritePath = "assets/sprites/monster/";
    private PlayerController _playerController;
    
    private CombatManager combatManager;
    private NarrationPanel _narrationPanel;

    private Story _selectedStory;
    
    public RoomView(PlayerController playerController, Story selectedStory) {
        this._playerController = playerController;
        this._selectedStory = selectedStory;

         this.combatManager = new CombatManager(playerController.getPlayer());
        setPreferredSize(new Dimension(16 * _displayTileSize, 16 * _displayTileSize));
        startSpaceKeyCheck(); 

    }

    public void displayRoom(Room room) {
        removeAll();

        _floorLayer = createLayerPanel(room.getFloorLayer(), _tilesetBasePath + "floor/", 0);
        _trapsLayer = createLayerPanel(room.getTrapsLayer(), _tilesetBasePath + "traps/", 1);
        _sideWallsLayer = createLayerPanel(room.getSideWallsLayer(), _tilesetBasePath + "side_walls/", 3);
        _sideDoorsLayer = createLayerPanel(room.getSideDoorsLayer(), _tilesetBasePath + "side_doors/", 4);
        _frontWallsLayer = createLayerPanel(room.getFrontWallsLayer(), _tilesetBasePath + "front_walls/", 5);
        _frontDoorsLayer = createLayerPanel(room.getFrontDoorsLayer(), _tilesetBasePath + "front_doors/", 6);
        _chestsLayer = createLayerPanel(room.getChestsLayer(), _tilesetBasePath + "chests/", 7);
        _pillarLayer = createLayerPanel(room.getPillarLayer(), _tilesetBasePath + "pillars/", 8);
        _decorationsLayer = createLayerPanel(room.getDecorationsLayer(), _tilesetBasePath + "decorations/", 9);
        
        _boostersLayer = createBoosterLayer(room);
        _monstersLayer = createMonsterLayer(room);
        _weaponsLayer = createWeaponLayer(room);

        
        add(_floorLayer, Integer.valueOf(0));
        add(_trapsLayer, Integer.valueOf(1));
        add(_sideWallsLayer, Integer.valueOf(3));
        add(_sideDoorsLayer, Integer.valueOf(4));
        add(_frontWallsLayer, Integer.valueOf(5));
        add(_frontDoorsLayer, Integer.valueOf(6));
        add(_chestsLayer, Integer.valueOf(7));
        add(_pillarLayer, Integer.valueOf(8));
        add(_decorationsLayer, Integer.valueOf(9));
        add(_boostersLayer, Integer.valueOf(2));
        add(_monstersLayer, Integer.valueOf(2));
        add(_weaponsLayer, Integer.valueOf(12));
        combatManager.setMonsters(room.getMonsters()); 

        repaint();
        revalidate();
        startMonsterMovement(room);
        combatManager.setRoom(room);
        
        if (_narrationPanel != null && !room.getName().equalsIgnoreCase("donjon2_room1")) {
            System.out.println("📢 Mise à jour de la narration...");
            _narrationPanel.updateRoomName(room.getName()); 
            String narrationText = _narrationPanel.generateRoomNarration(room);
            _narrationPanel.setNarrationWithEffect(narrationText);
            _playerController.getPlayer().addJournalEntry("📍 Vous êtes maintenant dans : " + room.getName());

        } else {
            System.out.println("⚠ Erreur : _narrationPanel est null !");
        }

    }
    
    private void grantClueToPlayer() {
        if (_selectedStory == null) return;

        String nextClue = _selectedStory.getNextClue();
        if (nextClue != null) {
            Clue clue = new Clue(nextClue);
            _playerController.getPlayer().addClue(clue);
            System.out.println("📜 **Nouvel indice obtenu :** " + nextClue);
        }
    }
    
    private void checkForTrapDamage() {
        Room currentRoom = _playerController.getRoomController().getCurrentRoom();
        int[][] trapsLayer = currentRoom.getTrapsLayer();
        
        int playerX = _playerController.getPlayer().getPositionX();
        int playerY = _playerController.getPlayer().getPositionY();

        if (trapsLayer != null && trapsLayer[playerY][playerX] != -1) { 
            System.out.println("⚠ Le joueur est sur une trappe ! Perte de 10 PV !");
            _playerController.getPlayer().modifyCharacteristic("life", -10);
            
            System.out.println("🛡 Joueur PV après piège : " + _playerController.getPlayer().getCharacteristic("life").getValue());
        }
    }
    
    private void startSpaceKeyCheck() {
        spaceKeyCheckTimer = new Timer(50, e -> {
            boolean isSpacePressed = _playerController.isSpaceKeyPressed();
            
            if (isSpacePressed) {
                combatManager.setPlayerAttacking(true);
                
                combatManager.updateCombat();       
                
                combatManager.setPlayerAttacking(false); 
            }
        });

        spaceKeyCheckTimer.start();
    }

    private JPanel createLayerPanel(int[][] layerGrid, String tilesetPath, int zIndex) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawLayer(g, layerGrid, tilesetPath);
            }
        };
        panel.setOpaque(false);
        panel.setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        return panel;
    }

    private JPanel createMonsterLayer(Room room) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMonsters(g, room);
            }
        };
        panel.setOpaque(false);
        panel.setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        return panel;
    }
    
    private void drawMonsters(Graphics g, Room room) {
        for (Monster monster : room.getMonsters()) {
            int spriteSize = monster.isLevel2() ? 32 : 16; 
            Image monsterSprite = loadMonsterImage(_monsterSpritePath + monster.getName().toLowerCase() + "_down.png", spriteSize);

            if (monsterSprite != null) {
                g.drawImage(
                    monsterSprite,
                    monster.getPositionX() * _displayTileSize,
                    monster.getPositionY() * _displayTileSize,
                    spriteSize * _scaleFactor, 
                    spriteSize * _scaleFactor,
                    this
                );
            }
        }
    }

    private Image loadMonsterImage(String path, int spriteSize) {
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("Image non trouvée : " + path);
            return null;
        }
        try {
            return ImageIO.read(file).getScaledInstance(spriteSize * _scaleFactor, spriteSize * _scaleFactor, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.err.println("Erreur de chargement : " + path);
            return null;
        }
    }
    
    private void startMonsterMovement(Room room) {
        if (monsterMovementTimer != null) {
            monsterMovementTimer.stop();
        }

        monsterMovementTimer = new Timer(500, e -> {
            moveMonsters(room);
            combatManager.setPlayer(_playerController.getPlayer());
            if (hasMonsters(room)) { 
                combatManager.updateCombat();
            }
            checkForTrapDamage(); 

            if (combatManager.getMonsterDefeated()) {
                System.out.println("monstre mort");

                grantClueToPlayer();
                
                combatManager.setMonsterDefeated(false);
            }
            repaint();
        });

        monsterMovementTimer.start();
    }

    private boolean hasMonsters(Room room) {
        return room.getMonsters() != null && !room.getMonsters().isEmpty(); 
    }

    
    private void moveMonsters(Room room) {
        int playerX = _playerController.getPlayer().getPositionX();
        int playerY = _playerController.getPlayer().getPositionY();

        for (Monster monster : room.getMonsters()) {
            if (monster.getLevel() > 1) {
                monster.setMovementStrategy(new ChasePlayerStrategy());
            } else { 
                monster.setMovementStrategy(new RandomMovementStrategy());
            }

            monster.move(playerX, playerY);
        }
    }
    
    private void drawLayer(Graphics g, int[][] layerGrid, String tilesetPath) {
        if (layerGrid == null) return;

        Map<Integer, Image> tileImages = loadImagesFromFolder(tilesetPath);
        for (int i = 0; i < layerGrid.length; i++) {
            for (int j = 0; j < layerGrid[i].length; j++) {
                int tileNumber = layerGrid[i][j];
                if (tileNumber < 0) continue;

                Image tileImage = tileImages.get(tileNumber);
                if (tileImage != null) {
                    g.drawImage(tileImage, j * _displayTileSize, i * _displayTileSize, _displayTileSize, _displayTileSize, this);
                }
            }
        }
    }

    private Map<Integer, Image> loadImagesFromFolder(String folderPath) {
        Map<Integer, Image> tileMap = new HashMap<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".png")) {
                    try {
                        int tileNumber = Integer.parseInt(file.getName().replace(".png", ""));
                        Image originalImage = ImageIO.read(file);
                        Image scaledImage = originalImage.getScaledInstance(_displayTileSize, _displayTileSize, Image.SCALE_SMOOTH);
                        tileMap.put(tileNumber, scaledImage);
                    } catch (Exception e) {
                        System.err.println("Erreur chargement image : " + file.getName());
                    }
                }
            }
        }
        return tileMap;
    }
    
    private JPanel createBoosterLayer(Room room) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoosters(g, room);
            }
        };
        panel.setOpaque(false);
        panel.setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        return panel;
    }
    
    private void drawBoosters(Graphics g, Room room) {
        Map<int[], ItemBooster> boosters = room.getBoosterPositions();
        for (Map.Entry<int[], ItemBooster> entry : boosters.entrySet()) {
            int[] position = entry.getKey();
            ItemBooster booster = entry.getValue();
            Image boosterSprite = loadBoosterImage(booster.getName());

            if (boosterSprite != null) {
                g.drawImage(boosterSprite, position[0] * _displayTileSize, position[1] * _displayTileSize, _displayTileSize, _displayTileSize, this);
            }
        }
    }
    
    private Image loadBoosterImage(String boosterName) {
        String imagePath = "assets/sprites/booster/" + boosterName + ".png";
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            System.err.println("Image de booster introuvable : " + imagePath);
            return null;
        }

        try {
            Image originalImage = ImageIO.read(imageFile);
            return originalImage.getScaledInstance(_displayTileSize, _displayTileSize, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'image du booster : " + boosterName);
            return null;
        }
    }
    
    private JPanel createWeaponLayer(Room room) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawWeapons(g, room);
            }
        };
        panel.setOpaque(false);
        panel.setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        return panel;
    }

    private void drawWeapons(Graphics g, Room room) {
        for (ItemWeapon weapon : room.getWeapons()) {
            Image weaponSprite = loadWeaponImage(weapon.getName());
            if (weaponSprite != null) {
                g.drawImage(weaponSprite, weapon.getPositionX() * _displayTileSize, weapon.getPositionY() * _displayTileSize, _displayTileSize, _displayTileSize, this);
            }
        }
    }
    
    private Image loadWeaponImage(String weaponName) {
        String imagePath = "assets/sprites/booster/" + weaponName + ".png";
        return loadImage(imagePath);
    }
    
    private Image loadImage(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("Image non trouvée : " + path);
            return null;
        }
        try {
            return ImageIO.read(file).getScaledInstance(_displayTileSize, _displayTileSize, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.err.println("Erreur de chargement : " + path);
            return null;
        }
    }
    
    public void setNarrationPanel(NarrationPanel narrationPanel) {
        this._narrationPanel = narrationPanel;
    }
}
