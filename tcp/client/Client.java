package tcp.client;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final String HOST = "127.0.0.1"; // localhost
        final int PORT = 9999;
        Socket socket;
        PrintStream out;
        Scanner teclado;
        Scanner in;

        try {
            socket = new Socket(HOST, PORT);
            System.out.println("Conectado com o servidor...");

            out = new PrintStream(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            teclado = new Scanner(System.in);

            String msg = "";
            while (!msg.equalsIgnoreCase("exit")) {
                System.out.println("Digite sua jogada (pedra, papel, tesoura) ou 'exit' para sair:");
                msg = teclado.nextLine();
                out.println(msg);

                if (!msg.equalsIgnoreCase("exit")) {
                    String serverResponse = in.nextLine();
                    System.out.println("Servidor: " + serverResponse);

                    serverResponse = in.nextLine();
                    System.out.println("Resultado: " + serverResponse);
                }
            }

            teclado.close();
            out.close();
            in.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }
    }
}
