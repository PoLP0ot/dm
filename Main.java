import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.* ;
import java.io.* ;

import javax.swing.JFrame;

/**
 * programme de test
 * version 0.0
 * date 13/09/2023
 * 
 */


public class Main extends JFrame{

Main(){
    System.out.println("Start");
    Matrix matrix = new Matrix();
    //display
    matrix.display();
    
    GUI gui = new GUI(matrix);
    setContentPane(gui);
    setJMenuBar(gui.menuBar);
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
}

/**     
 * @param ]
 */
public static void main(String args[ ]){
    new Main();
    
}

public static void connecttoserver(String string){
    try {
        //ouverture de la socket et des streams
        Socket sock = new Socket("localhost",10000);

        DataOutputStream out =new DataOutputStream(sock.getOutputStream());
        DataInputStream in = new DataInputStream(sock.getInputStream());
        String playerName = "";
        // Envoyer le protocole et le nom du joueur
        out.writeUTF(Protocol.PLAYERNAME.name());
        out.writeUTF(playerName);

         // Communication avec le serveur ici...
         while(true) {
            Protocol protocol = Protocol.valueOf(in.readUTF());
            switch(protocol) {
                case DIM:
                    // Recevoir la dimension du jeu ici...
                    int dimension = in.readInt();
                    System.out.println("La dimension du jeu est: " + dimension);
                    break;
                case START:
                    // Commencer la partie ici...
                    System.out.println("La partie commence !");
                    break;
                case END:
                    System.out.println("Partie terminée.");
                    sock.close();
                    return;
                //... autres cas
            }
        }

    } catch (UnknownHostException e) {
        System.out.println("L'hôte est inconnu");
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}