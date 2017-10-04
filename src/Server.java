
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Server implements Runnable {
	public int qtdReq;
	public int port;
	public String ip;
	public String serverName;

	public Server(String serverName, String ip, int port) {
		this.qtdReq = 0;
		this.ip = ip;
		this.port = port;
		this.serverName = serverName;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public void run() {
		long tempoInicio = System.currentTimeMillis();
		ServerSocket socketServidor;
		String texto;
		PrintStream saida;
		try {
			socketServidor = new ServerSocket(this.port);
			System.out.println("Aguardando conexão com o cliente");
			while (true) {

				Socket connectionSocket = socketServidor.accept();
				saida = new PrintStream(connectionSocket.getOutputStream());
				System.out.println("Cliente conectado: " + connectionSocket.getInetAddress().getHostAddress()
						+ " ao servidor: " + this.serverName);
				Scanner entrada = new Scanner(connectionSocket.getInputStream());

				while (entrada.hasNext()) {
					texto = entrada.nextLine();
					if (texto.equals("/REQNUM")) {
						saida.println("Quantidade de requisições: " + this.qtdReq);
						System.out.println("Entrada: " + texto);
					} else if (texto.equals("/UPTIME")) {
						long total = System.currentTimeMillis() - tempoInicio;
						saida.println("Tempo de execução do " + this.serverName + ": "
								+ String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(total),
										TimeUnit.MILLISECONDS.toMinutes(total)
												- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(total)),
										TimeUnit.MILLISECONDS.toSeconds(total)
												- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(total))));
					} else {
						this.qtdReq++;
						saida.println(texto);
						System.out.println("Entrada: " + texto);
					}
				}

				entrada.close();
				connectionSocket.close();
			}

		} catch (IOException e) {
			System.out.println("Erro: " + e);
		}
	}

}
