
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente implements Runnable {
	private Socket cliente;

	public Cliente(String ip, int porta) throws UnknownHostException, IOException {
		this.cliente = new Socket(ip, porta);
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		Scanner teclado = new Scanner(System.in);
		System.out.println("Informe IP e Porta, respectivamente!");
		Cliente cli = new Cliente(teclado.nextLine(), teclado.nextInt());
		Thread t = new Thread(cli);
		t.start();

	}

	@Override
	public void run() {

		try {
			PrintStream saida;
			Scanner entrada = new Scanner(this.cliente.getInputStream());
			System.out.println("Cliente conectou");
			String texto;
			Scanner teclado = new Scanner(System.in);

			saida = new PrintStream(this.cliente.getOutputStream());
			while (teclado.hasNext()) {
				texto = teclado.nextLine();
				if (!texto.equals("/CLOSE")) {
					saida.println(texto);
					System.out.println("Requisição: " + texto);
					System.out.println("Resposta: " + entrada.nextLine());
				} else {
					saida.close();
					teclado.close();
					this.cliente.close();
					System.out.println("terminou");
				}
			}

			teclado.close();
			entrada.close();
		} catch (IOException e) {
			System.out.println("Erro: " + e);
		}

	}

}
