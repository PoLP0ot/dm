import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

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





    public static void connectToServer(String playerName) {
        try (Socket sock = new Socket("localhost", 10000);
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            DataInputStream in = new DataInputStream(sock.getInputStream())
            ){
                // Écoute des messages du serveur dans un nouveau thread
                new Thread(new ClientHandler(sock, playerName)).start();
;
        } catch (UnknownHostException e) {
            System.out.println("L'hôte est inconnu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}

