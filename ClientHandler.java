import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler implements Runnable {
        public Socket socket;
        public int clientId;
        private Matrix matrix= new Matrix();

        public ClientHandler(Socket socket, int clientId, Matrix matrix) {
            this.socket = socket;
            this.clientId = clientId;
            this.matrix = matrix;
        }

        @Override
        public void run() {
            try (DataInputStream input = new DataInputStream(socket.getInputStream());
                 DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
                    System.out.println("ClientHandler démarré pour le client: " + clientId);
                // Lire le nom du joueur
                 String playerName = "";
                System.out.println("Joueur connecté: " + playerName);

               ;

        //while(true) {
            Protocol protocol = Protocol.valueOf(input.readUTF());
            switch(protocol) {
                case PLAYERNAME:
                    playerName = input.readUTF();
                    System.out.println("Joueur connecté: " + playerName);
                    break;
                case DIM:
                    // Envoi de la dimension au joueur...
                    int dimension = matrix.getDim();  // Vous devez implémenter cette méthode selon votre logique de jeu.
                    output.writeUTF(Protocol.DIM.name());
                    output.writeInt(dimension);
                    output.flush();
                    break;
                case START:
                    // Lancer la partie ici

                    //startGame(); 
                    break;
                case END:
                    System.out.println(playerName + " a quitté la partie.");
                    return;
            }}   catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null) socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }