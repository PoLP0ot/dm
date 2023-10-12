import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Case extends JPanel{
        boolean mine;
        boolean flag;
        boolean revealed;
        int nbMinesAutour;
        private GUI gui;
      

        Case () {
            this.mine = false;
            this.flag = false;
            this.revealed = false;
            
        
        }
        
        public Case(int i, int j, GUI gui) {
            this.gui = gui;
            Matrix matrix= gui.getMatrix();
            if(matrix.getNum(i, j) == true)
                this.mine = matrix.getNum(i, j);
            this.nbMinesAutour = matrix.computeMinesNumber(i, j);
        setPreferredSize(new Dimension(30, 30)); // Définir la taille par défaut
        setBackground(Color.GRAY); // Couleur par défaut
        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Bordure noire par défaut

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
                
            }
        });
    }

    private void handleMouseClick(MouseEvent e) {
        // Exemple : Révéler la case sur un clic gauche
        if (SwingUtilities.isLeftMouseButton(e)) {
            gui.compteurclick++;
            System.out.println(gui.currentLevel.getTaille()*gui.currentLevel.getTaille()-gui.currentLevel.getMines() );
            System.out.println(gui.compteurclick);
            
            if (gui.compteurclick == gui.currentLevel.getTaille()*gui.currentLevel.getTaille()-gui.currentLevel.getMines() ) {
                reveal();
               int choice = JOptionPane.showOptionDialog(this, "Gagné !", "Démineur", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Relancer", "Quitter"}, "Relancer");
                 
                if (choice == JOptionPane.YES_OPTION) {
                    //relance une grille easy
                    gui.resetGrid(Levels.EASY);
                } else {
                    System.exit(0);  // Quittez l'application
                }
                
                    revalidate();
                    repaint(); // Redessine le composant
                }
            
            reveal();
        }
        // Exemple : Placer un drapeau sur un clic droit
        else if (SwingUtilities.isRightMouseButton(e)) {
            toggleMark();
        }

    }

    public void reveal() {
        if (!mine && !revealed) {
            revealed = true;
            setBackground(Color.WHITE);

            JLabel label = new JLabel(String.valueOf(nbMinesAutour));
            label.setHorizontalAlignment(JLabel.CENTER);  
            add(label);
            revalidate();   
            repaint();  // Redessine le composant
        }
        else if (mine && !revealed) {
            revealed = true;
            setBackground(Color.RED);
            gui.endGame();
            
            ImageIcon bombIcon = getScaledImageIcon(("ressources/bomb.png"));
            JLabel bombLabel = new JLabel(bombIcon);
            add(bombLabel);
            
            
            revalidate();
            repaint();

            // Affichez la boîte de dialogue
             int choice = JOptionPane.showOptionDialog(this, "Perdu looser !", "Démineur", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Relancer", "Quitter"}, "Relancer");
        
        if (choice == JOptionPane.YES_OPTION) {
            //relance une grille easy
            gui.resetGrid(Levels.EASY);
        } else {
            System.exit(0);  // Quittez l'application
        }
        
            revalidate();
            repaint(); // Redessine le composant
        }
        
        }
    

    
    public void toggleMark() {
        if (!revealed) {
            flag = !flag;
            if (flag) {
                ImageIcon flagIcon = new ImageIcon(getClass().getResource("ressources/flag.png" ));
                JLabel flagLabel = new JLabel(flagIcon);
                add(flagLabel);
                setBackground(Color.BLUE);
            } else {
                setBackground(Color.GRAY);
            }
        }
        gui.updateFlagCount(flag);
    }

    public ImageIcon getScaledImageIcon(String path) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(getClass().getResource(path));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Image dimg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            return new ImageIcon(dimg);
        }

    
}
    