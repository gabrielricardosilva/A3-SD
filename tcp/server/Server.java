package tcp.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Random;

public class Server {
//*Jokempo */
    private static final String[] OPCOES = {"PEDRA", "PAPEL", "TESOURA"};

    public static void main(String[] args) {
        final int PORT = 9999;
        ServerSocket serverSocket;
        Socket clientSocket;
        Scanner in;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor disponível na porta: " + PORT);
            while (true) {
                System.out.println("Aguardando conexao...");
                clientSocket = serverSocket.accept();
                in = new Scanner(clientSocket.getInputStream());

                String msg = "";
                while (!msg.equalsIgnoreCase("exit")) {
                    System.out.println("Aguardando mensagem...");
                    msg = in.nextLine();
                    System.out.println("Recebido: " + msg);
                }
//*Escolher o modo de jogo */
            DataInputStream in1;
            String modo = in1.readUTF();
                if ("CPU".equalsIgnoreCase(modo)) {
                    jogarContraCpu(clientSocket, in1, out);
                } else if ("JOGADOR".equalsIgnoreCase(modo)) {
                    Socket clientSocket2 = serverSocket.accept();
                    System.out.println("Segundo jogador conectado.");

                    DataInputStream in2 = new DataInputStream(clientSocket2.getInputStream());
                    DataOutputStream out2 = new DataOutputStream(clientSocket2.getOutputStream());

                    jogarContraJogador(clientSocket, clientSocket2, in1, out1, in2, out2);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
            return;
        }

//*Escolha do Player */
private static void jogarContraCpu(Socket clientSocket, DataInputStream in, DataOutputStream out) throws IOException {
    while (true) {
        String escolhaJogador = in.readUTF();
        String escolhaCpu = OPCOES[new Random().nextInt(OPCOES.length)];
        String resultado = determinarResultado(escolhaJogador, escolhaCpu);

        out.writeUTF(escolhaCpu);
        out.writeUTF(resultado);
    }   
    }

    private static void jogarContraJogador(Socket clientSocket1, Socket clientSocket2, DataInputStream in1, DataOutputStream out1, DataInputStream in2, DataOutputStream out2) throws IOException {
        while (true) {
            String escolhaJogador1 = in1.readUTF();
            String escolhaJogador2 = in2.readUTF();
            String resultado = determinarResultado(escolhaJogador1, escolhaJogador2);

            out1.writeUTF(escolhaJogador2);
            out1.writeUTF(resultado);

            out2.writeUTF(escolhaJogador1);
            out2.writeUTF(resultado);
        }
    }

    private static String determinarResultado(String escolha1, String escolha2) {
        if (escolha1.equals(escolha2)) {
            return "EMPATE";
        }
        switch (escolha1) {
            case "PEDRA":
                return (escolha2.equals("TESOURA")) ? "JOGADOR 1 VENCEU" : "JOGADOR 2 VENCEU";
            case "PAPEL":
                return (escolha2.equals("PEDRA")) ? "JOGADOR 1 VENCEU" : "JOGADOR 2 VENCEU";
            case "TESOURA":
                return (escolha2.equals("PAPEL")) ? "JOGADOR 1 VENCEU" : "JOGADOR 2 VENCEU";
        }
        return "RESULTADO INDETERMINADO";
    


/*         try {
            in.close();
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            // TODO: handle exception
        } */
    }

}