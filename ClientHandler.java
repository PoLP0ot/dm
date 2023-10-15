import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler implements Runnable {
    public Socket socket;
    private Matrix matrix;
    private String playerName;

    public ClientHandler(Socket socket, String playerName) {
        this.socket = socket;
        this.playerName = playerName;
    }

    @Override
    public void run() {
        try (
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ){
            output.writeUTF(Protocol.PLAYERNAME.name());
            output.writeUTF(playerName);
            System.out.println("Joueur connecté: " + playerName);

            while(true) {
                Protocol protocol = Protocol.valueOf(input.readUTF());
                switch(protocol) {
                    case MATRIX_DIM:
                        int dimension = input.readInt();
                        matrix = new Matrix(dimension);  // Vous devrez peut-être ajouter un nouveau constructeur à Matrix
                        break;
                    case MATRIX_DATA:
                        for(int i=0; i<matrix.getDim(); i++) {
                            for(int j=0; j<matrix.getDim(); j++) {
                                matrix.setNum(i, j, input.readBoolean());
                            }
                        }
                        break;
                    case START:
                        // Lancer la partie ici
                        break;
                    case END:
                        System.out.println(playerName + " a quitté la partie.");
                        return;
                }
            }
        } catch (IOException e) {
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
