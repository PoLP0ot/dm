import java.net.ServerSocket;
import java.net.Socket;
import java.awt.List;
import java.util.ArrayList;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class Serveur{

    final static int PORT = 10000;
    private static Matrix sharedmatrix = new Matrix();
    private static ArrayList<ClientHandler> clients = new ArrayList<>();

    Serveur(){
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur en attente de connexion");
            
            int clientCount = 0;
            
            while (true) {
                Socket socket = serverSocket.accept();
                clientCount++;
                System.out.println("Connexion établie avec le client " + clientCount);
                
                // Création d'un nouveau thread pour chaque nouveau client
                ClientHandler clientHandler = new ClientHandler(socket, clientCount, sharedmatrix);
                new Thread(clientHandler).start();
                clients.add(clientHandler);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Démarrage Serveur");
        new Serveur();
    }
}