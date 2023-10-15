import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;



public class GUI extends JPanel {
    JLabel title = new JLabel("Xmines");
    JLabel difficulty = new JLabel("Difficulty");
    JPanel gridPanel = new JPanel();
    JPanel North = new JPanel();
    JButton restartButton = new JButton("Restart");
    JMenuBar menuBar = new JMenuBar(); 
    JLabel mineCounter = new JLabel("Mines: 0");
    int flagCount = 0;
    Matrix currentMatrix;
    Levels currentLevel=Levels.EASY;
    JLabel timerLabel = new JLabel("Time: 0");
    int elapsedTime = 0;
    Timer timer;
    int compteurclick = 0;



    GUI(){
        this(new Matrix());
    }

    GUI(Matrix matrix){

        this.currentMatrix = matrix;
        gridPanel.setLayout(new GridLayout(matrix.getDim(), matrix.getDim()));
        setLayout(new BorderLayout());

        for(int i=0; i<matrix.getDim(); i++){
            for (int j=0; j<matrix.getDim(); j++)
                if (matrix.getNum(i, j)==true)
                    gridPanel.add(new Case(i,j,this));
                else{
                    gridPanel.add(new Case(i,j,this));
                }
        }

        
      //timer initialisation
        timer = new Timer(1000, e -> {
            elapsedTime++;
            timerLabel.setText("Time: " + elapsedTime);
        });

        //style du bouton Restart
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.setBackground(Color.BLACK);
        restartButton.setForeground(Color.WHITE);
        restartButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        //configuration du bouton restart
        restartButton.addActionListener(e -> {
            System.out.println("Restart button clicked");
            resetGrid(currentLevel);
        });

        

        
       // Configuration de la JMenuBar
        JMenu fileMenu = new JMenu("File");
        JMenuItem quitMenuItem = new JMenuItem("Quit");
        
        quitMenuItem.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        fileMenu.add(quitMenuItem);
        JMenuItem onlineMenuItem = new JMenuItem("Partie en ligne");
        onlineMenuItem.addActionListener((ActionEvent e) -> {
            System.out.println("Partie en ligne");
            startOnlineGame();
            
        });
        fileMenu.add(onlineMenuItem);

        //configuration des différents Levels
        JMenu levelMenu = new JMenu("Level");
        for (Levels level : Levels.values()) {
            JMenuItem levelMenuItem = new JMenuItem(level.name());
            levelMenuItem.addActionListener(e -> {
                String selectedLevel = e.getActionCommand();
                System.out.println("Selected level: " + selectedLevel);
                Levels levelse = Levels.valueOf(selectedLevel);
                currentLevel = levelse;
                resetGrid(levelse);
            });
            levelMenu.add(levelMenuItem);
        }

        //Afficher le bon nombre de mines en fonction du niveau choisi
        updateMineCounter(matrix.computeAllmines());

        //lancement timer
        startGame();

        

        //Ajout des différents éléments aux différents panels
        menuBar.add(fileMenu);
        menuBar.add(levelMenu);
        add(gridPanel, BorderLayout.CENTER);
        North.add(title, BorderLayout.NORTH);
        North.add(difficulty, BorderLayout.SOUTH);
        add(North, BorderLayout.NORTH);
        add(restartButton, BorderLayout.SOUTH);
        North.add(mineCounter);
        North.add(timerLabel);


}  

//fonction qui permet de reset la grille en fonction du niveau choisi
public void resetGrid(Levels selectedLevel) {
    gridPanel.removeAll(); //suppresion cases 
    updateMineCounter(selectedLevel.getMines()); //mise à jour du compteur de mines
    compteurclick=0; //remise à 0 du compteur de clics  

    switch (selectedLevel) {
        case EASY:
            this.currentMatrix = new Matrix(Levels.EASY.getMines(), Levels.EASY.getTaille()); 
            break;
        case MEDIUM:
            this.currentMatrix = new Matrix(Levels.MEDIUM.getMines(), Levels.MEDIUM.getTaille()); 
            break;
        case HARD:
            this.currentMatrix = new Matrix(Levels.HARD.getMines(), Levels.HARD.getTaille()); 
            break;
        default:
            this.currentMatrix = new Matrix(); 
            break;
    }

    gridPanel.setLayout(new GridLayout(currentMatrix.getDim(), currentMatrix.getDim()));
    for (int i = 0; i < currentMatrix.getDim(); i++) {
        for (int j = 0; j < currentMatrix.getDim(); j++) {
            if (currentMatrix.getNum(i, j) == true) {
                gridPanel.add(new Case(i, j,this));
            } else {
                gridPanel.add(new Case(i, j,this));
            }
        }
   
    }

    //lancement du chrono
    startGame();

    // Redessine le composant 
    gridPanel.revalidate();
    gridPanel.repaint();

}

//mise à jour du compteur de mines
public void updateMineCounter(int remainingMines) {
        mineCounter.setText("Mines restantes: " + remainingMines);
    }

//mise à jour du compteur de drapeaux
public void updateFlagCount(boolean flagAdded) {
        if (flagAdded) {
            flagCount++;
        } else {
            flagCount--;
        }
        updateMineCounter(this.currentMatrix.computeAllmines() - flagCount);
        System.out.println(this.currentMatrix.computeAllmines());
    }

//fonction qui permet de récupérer la matrice
Matrix getMatrix(){
        return this.currentMatrix;
    }


//lancement timer
public void startGame() {
    elapsedTime = 0;
    timerLabel.setText("Time: " + elapsedTime);
    timer.start();
}

//arret timer
public void endGame() {
    timer.stop();
}
/* 
public void revealAllMines() {
    // Parcourir l'ensemble de la grille
    for(int i = 0; i < matrix.getWidth(); i++) {
        for(int j = 0; j < matrix.getHeight(); j++) {
            Case currentCase = getNum(i, j);
            // Si la case est une mine
            if (currentCase.mine) {
                currentCase.revealMine();
            }
        }
    }
}*/
 private void startOnlineGame() {
        // Boîte de dialogue pour le nom du joueur
        String playerName = JOptionPane.showInputDialog(null, 
                            "Veuillez entrer votre nom:", 
                            "Nom du Joueur", 
                            JOptionPane.PLAIN_MESSAGE);

        // Si le joueur clique sur annuler
        if (playerName == null || playerName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nom invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
             // ou gérer autrement selon vos besoins
        }

        // Boîte de dialogue pour choisir un niveau
        /*String[] options = {"Facile", "Moyen", "Difficile"};
        JComboBox<String> combo = new JComboBox<>(options);
        String msg = "Choisissez un niveau:";
        Object[] messages = {msg, combo};
        JOptionPane.showMessageDialog(null, messages, "Niveau", JOptionPane.PLAIN_MESSAGE);

        String level = (String) combo.getSelectedItem();*/

        Main.connectToServer(playerName);
    }
}

