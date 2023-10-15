import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Serveur {
    private static final int PORT = 10000;
    private static final List<Socket> clients = new ArrayList<>();
    static int clientcount = 0;
    static Matrix sharedmatrix = new Matrix();
    
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur en attente de connexion");
            
            while (clientcount < 2) {
                Socket socket = serverSocket.accept();
                clients.add(socket);
                handleClient(socket);
                clientcount++;
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        new Thread(() -> {
            try (
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream())
                 ){


                String playerName = in.readUTF();
                System.out.println("Joueur connecté: " + playerName);

                out.writeUTF("Bienvenue " + playerName + "!");
                
                // Envoyer la dimension de la matrice
                out.writeUTF(Protocol.MATRIX_DIM.name());
                out.writeInt(sharedmatrix.getDim());

                // Envoyer les données de la matrice
                out.writeUTF(Protocol.MATRIX_DATA.name());
                for(int i=0; i<sharedmatrix.getDim(); i++) {
                    for(int j=0; j<sharedmatrix.getDim(); j++) {
                        out.writeBoolean(sharedmatrix.getNum(i, j));
                    }
                }
                 while(true) {
                // Ajoutez une gestion des exceptions ici pour gérer EOFException
                try {
                    Protocol protocol = Protocol.valueOf(in.readUTF());
                    // Gérer les messages entrants ici
                } catch (EOFException e) {
                    System.out.println("Client déconnecté");
                    break;
                }
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}








/* 
    public static void main(String[] args) {
        System.out.println("Démarrage Serveur");
        new Serveur();
    }
}*/